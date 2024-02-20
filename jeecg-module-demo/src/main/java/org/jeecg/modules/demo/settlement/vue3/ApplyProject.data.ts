import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import {JVxeTypes,JVxeColumn} from '/@/components/jeecg/JVxeTable/types'
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '项目名称',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '项目编号',
    align:"center",
    dataIndex: 'code'
   },
   {
    title: '所属单位',
    align:"center",
    dataIndex: 'unit_dictText'
   },
   {
    title: '负责主体',
    align:"center",
    dataIndex: 'owner_dictText'
   },
   {
    title: '开始时间',
    align:"center",
    dataIndex: 'startDate'
   },
   {
    title: '结束时间',
    align:"center",
    dataIndex: 'endDate'
   },
   {
    title: '中标单位',
    align:"center",
    dataIndex: 'bidder_dictText'
   },
   {
    title: '描述',
    align:"center",
    dataIndex: 'description'
   },
   {
    title: '附件',
    align:"center",
    dataIndex: 'file',
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "项目名称",
      field: "name",
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "项目编号",
      field: "code",
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "中标单位",
      field: "bidder",
      component: 'JDictSelectTag',
      componentProps:{
          dictCode:"sys_depart,depart_name,id"
      },
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '项目名称',
    field: 'name',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入项目名称!'},
          ];
     },
  },
  {
    label: '项目编号',
    field: 'code',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入项目编号!'},
          ];
     },
  },
  {
    label: '所属单位',
    field: 'unit',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"sys_depart,depart_name,id"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入所属单位!'},
          ];
     },
  },
  {
    label: '负责主体',
    field: 'owner',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
  },
  {
    label: '开始时间',
    field: 'startDate',
    component: 'DatePicker',
    componentProps: {
       showTime:true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入开始时间!'},
          ];
     },
  },
  {
    label: '结束时间',
    field: 'endDate',
    component: 'DatePicker',
    componentProps: {
       showTime:true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入结束时间!'},
          ];
     },
  },
  {
    label: '中标单位',
    field: 'bidder',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"sys_depart,depart_name,id"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入中标单位!'},
          ];
     },
  },
  {
    label: '描述',
    field: 'description',
    component: 'InputTextArea',
  },
  {
    label: '附件',
    field: 'file',
    component: 'JUpload',
    componentProps:{
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
//子表表格配置
export const applyContractColumns: JVxeColumn[] = [
    {
      title: '合同编号',
      key: 'contractNum',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
        validateRules: [
          { required: true, message: '${title}不能为空' },
        ],
    },
    {
      title: '合同名称',
      key: 'contractName',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
        validateRules: [
          { required: true, message: '${title}不能为空' },
        ],
    },
    {
      title: '签订时间',
      key: 'signDate',
      type: JVxeTypes.date,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
        validateRules: [
          { required: true, message: '${title}不能为空' },
        ],
    },
    {
      title: '合同类型',
      key: 'type',
      type: JVxeTypes.select,
      options:[],
      dictCode:"contract_type",
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
        validateRules: [
          { required: true, message: '${title}不能为空' },
        ],
    },
    {
      title: '合同状态',
      key: 'status',
      type: JVxeTypes.select,
      options:[],
      dictCode:"contract_status",
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '开工时间',
      key: 'startDate',
      type: JVxeTypes.date,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
        validateRules: [
          { required: true, message: '${title}不能为空' },
        ],
    },
    {
      title: '竣工时间',
      key: 'endDate',
      type: JVxeTypes.date,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
        validateRules: [
          { required: true, message: '${title}不能为空' },
        ],
    },
    {
      title: '总价',
      key: 'totalPrice',
      type: JVxeTypes.inputNumber,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
        validateRules: [
          { required: true, message: '${title}不能为空' },
        ],
    },
  ]
export const applyFilesColumns: JVxeColumn[] = [
    {
      title: '文件名称',
      key: 'fileName',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '文件类型',
      key: 'bizType',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '是否可用',
      key: 'flag',
      type: JVxeTypes.checkbox,
      customValue: [0,1],
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '描述',
      key: 'description',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '分册',
      key: 'fc',
      type: JVxeTypes.select,
      options:[],
      dictCode:"apply_fc",
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '文件',
      key: 'file',
      type: JVxeTypes.file,
      token:true,
      responseName:"message",
      width:"200px",
      placeholder: '请选择文件',
      defaultValue:'',
    },
    {
      title: '项目外键',
      key: 'projectId',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
  ]


/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
// 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}