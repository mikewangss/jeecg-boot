import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import {JVxeTypes,JVxeColumn} from '/@/components/jeecg/JVxeTable/types'
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '手机号',
    align:"center",
    dataIndex: 'phoneNumber'
   },
   {
    title: '债券人姓名',
    align:"center",
    dataIndex: 'bondholdersName'
   },
   {
    title: '证件号',
    align:"center",
    dataIndex: 'idCode'
   },
   {
    title: '收款银行',
    align:"center",
    dataIndex: 'bankName'
   },
   {
    title: '银行账户',
    align:"center",
    dataIndex: 'bankAccount'
   },
   {
    title: '户名',
    align:"center",
    dataIndex: 'accountName'
   },
   {
    title: '金额',
    align:"center",
    dataIndex: 'money'
   },
   {
    title: '债权类型',
    align:"center",
    dataIndex: 'bondholdersType_dictText'
   },
   {
    title: '说明',
    align:"center",
    dataIndex: 'remark'
   },
   {
    title: '附件',
    align:"center",
    dataIndex: 'file',
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '手机号',
    field: 'phoneNumber',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入手机号!'},
          ];
     },
  },
  {
    label: '债券人姓名',
    field: 'bondholdersName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入债券人姓名!'},
          ];
     },
  },
  {
    label: '证件号',
    field: 'idCode',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入证件号!'},
          ];
     },
  },
  {
    label: '收款银行',
    field: 'bankName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入收款银行!'},
          ];
     },
  },
  {
    label: '银行账户',
    field: 'bankAccount',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入银行账户!'},
          ];
     },
  },
  {
    label: '户名',
    field: 'accountName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入户名!'},
          ];
     },
  },
  {
    label: '金额',
    field: 'money',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入金额!'},
          ];
     },
  },
  {
    label: '债权类型',
    field: 'bondholdersType',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"zqlx"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入债权类型!'},
          ];
     },
  },
  {
    label: '说明',
    field: 'remark',
    component: 'InputTextArea',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入说明!'},
          ];
     },
  },
  {
    label: '附件',
    field: 'file',
    component: 'JUpload',
    componentProps:{
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入附件!'},
          ];
     },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];
//子表单数据
export const applyWorkflowFormSchema: FormSchema[] = [
  {
    label: '审核阶段',
    field: 'state',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"apply_state"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入审核阶段!'},
          ];
     },
  },
  {
    label: '审核状态',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"apply_status"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入审核状态!'},
          ];
     },
  },
  {
    label: '审核结果',
    field: 'info',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入审核结果!'},
          ];
     },
  },
  {
    label: '申请编号',
    field: 'applyId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入申请编号!'},
          ];
     },
  },
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];
//子表表格配置


/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
// 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}