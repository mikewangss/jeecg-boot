<template>
  <div>
    <BasicForm @register="registerForm" ref="formRef"/>
    <!-- 子表单区域 -->
    <a-tabs v-model:activeKey="activeKey" animated  @change="handleChangeTabs">
          <a-tab-pane tab="结算申请流程" key="applyWorkflow" :forceRender="true">
            <ApplyWorkflowForm ref="applyWorkflowForm" :disabled="formDisabled"></ApplyWorkflowForm>
          </a-tab-pane>

    </a-tabs>

    <div style="width: 100%;text-align: center" v-if="!formDisabled">
      <a-button @click="handleSubmit" pre-icon="ant-design:check" type="primary">提 交</a-button>
    </div>
  </div>
</template>

<script lang="ts">

  import {BasicForm, useForm} from '/@/components/Form/index';
  import { computed, defineComponent, reactive, ref, unref } from 'vue';
  import {defHttp} from '/@/utils/http/axios';
  import { propTypes } from '/@/utils/propTypes';
  import { useJvxeMethod } from '/@/hooks/system/useJvxeMethods';
  import { VALIDATE_FAILED } from '/@/utils/common/vxeUtils';
  import ApplyWorkflowForm from './ApplyWorkflowForm.vue'
  import {getBpmFormSchema} from '../ApplyInfo.data';
  import {saveOrUpdate,applyWorkflowDTOList} from '../ApplyInfo.api';

  export default defineComponent({
    name: "ApplyInfoForm",
    components:{
      BasicForm,
      ApplyWorkflowForm,
    },
    props:{
      formData: propTypes.object.def({}),
      formBpm: propTypes.bool.def(true),
    },
    setup(props){
      const [registerForm, { setFieldsValue, setProps }] = useForm({
        labelWidth: 150,
        schemas: getBpmFormSchema(props.formData),
        showActionButtonGroup: false,
        baseColProps: {span: 24}
      });

      const formDisabled = computed(()=>{
        if(props.formData.disabled === false){
          return false;
        }
        return true;
      });

      const refKeys = ref(['applyWorkflow', ]);
      const activeKey = ref('applyWorkflow');
      const applyWorkflowForm = ref();
      const tableRefs = {};

      const [handleChangeTabs,handleSubmit,requestSubTableData,formRef] = useJvxeMethod(requestAddOrEdit,classifyIntoFormData,tableRefs,activeKey,refKeys,validateSubForm);

      function classifyIntoFormData(allValues) {
        let main = Object.assign({}, allValues.formValue)
        return {
          ...main, // 展开
          applyWorkflowDTOList: applyWorkflowForm.value.getFormData(),
        }
      }
      //校验所有一对一子表表单
      function validateSubForm(allValues){
        return new Promise((resolve, _reject)=>{
          Promise.all([
            applyWorkflowForm.value.validateForm(0),
          ]).then(() => {
            resolve(allValues)
          }).catch(e => {
            if (e.error === VALIDATE_FAILED) {
              // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
              activeKey.value = e.index == null ? unref(activeKey) : refKeys.value[e.index]
            } else {
              console.error(e)
            }
          })
        })
      }

      //表单提交事件
      async function requestAddOrEdit(values) {
        await saveOrUpdate(values, true);
      }

      const queryByIdUrl = '/settlement/applyInfo/queryById';
      async function initFormData(){
        let params = {id: props.formData.dataId};
        const data = await defHttp.get({url: queryByIdUrl, params});
        //设置表单的值
        await setFieldsValue({...data});
        applyWorkflowForm.value.initFormData(applyWorkflowDTOList, data.id);
        //默认是禁用
        await setProps({disabled: formDisabled.value})
      }

      initFormData();

      return {
        registerForm,
        formDisabled,
        formRef,
        handleSubmit,
        activeKey,
        handleChangeTabs,
        applyWorkflowForm,
      }
    }
  });
</script>