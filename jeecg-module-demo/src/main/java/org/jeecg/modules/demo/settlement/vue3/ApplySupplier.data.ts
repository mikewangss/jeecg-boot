import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '供应商名称',
    align:"center",
    dataIndex: 'supplierName'
   },
   {
    title: '法人姓名',
    align:"center",
    dataIndex: 'legalPersonName'
   },
   {
    title: '法人身份证',
    align:"center",
    dataIndex: 'legalPersonIdcard'
   },
   {
    title: '法人电话',
    align:"center",
    dataIndex: 'legalPersonPhone'
   },
   {
    title: '公司地址',
    align:"center",
    dataIndex: 'firmAddress'
   },
   {
    title: '营业执照开始日期',
    align:"center",
    dataIndex: 'businessLicenseStartDate'
   },
   {
    title: '营业执照结束日期',
    align:"center",
    dataIndex: 'businessLicenseEndDate'
   },
   {
    title: '营业执照',
    align:"center",
    dataIndex: 'businessLicenseFile',
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '供应商名称',
    field: 'supplierName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入供应商名称!'},
          ];
     },
  },
  {
    label: '法人姓名',
    field: 'legalPersonName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入法人姓名!'},
          ];
     },
  },
  {
    label: '法人身份证',
    field: 'legalPersonIdcard',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入法人身份证!'},
          ];
     },
  },
  {
    label: '法人电话',
    field: 'legalPersonPhone',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入法人电话!'},
          ];
     },
  },
  {
    label: '公司地址',
    field: 'firmAddress',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入公司地址!'},
          ];
     },
  },
  {
    label: '营业执照开始日期',
    field: 'businessLicenseStartDate',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入营业执照开始日期!'},
          ];
     },
  },
  {
    label: '营业执照结束日期',
    field: 'businessLicenseEndDate',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入营业执照结束日期!'},
          ];
     },
  },
  {
    label: '营业执照',
    field: 'businessLicenseFile',
    component: 'JUpload',
    componentProps:{
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入营业执照!'},
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



/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}