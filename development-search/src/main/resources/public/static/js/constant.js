/*选项*/
var GRID_OPTIONS = {SINGLE:1,MULTIPLE:2}

/*是否*/
var OPTION_WHETHER = [{value:1,text:"是"},{value:0,text:"否"}];

/*按钮(行内，导航按钮)*/
var OPTION_BUTTON = [{value:1,text:"内部"},{value:0,text:"外部"}];

/*数据字典控件类型*/
var DICT_COMPONENTTYPE = 
[{value:"TEXT",text:"文本框"},
 {value:"DROPDOWN",text:"下拉框"},
 {value:"SELECTOR",text:"选择器"},
 {value:"TEXTAREA",text:"文本域"},
 {value:"GENERATECODE",text:"自动编码"},
 {value:"CHECKBOX",text:"单选框"},
 {value:"DATEPICKER",text:"日期"}
];

/*表达式*/
var DICT_EXPRESSION = [
    {value:"=",text:"="},
	{value:"LIKE",text:"LIKE"},
	{value:">",text:">"},
	{value:">=",text:">="},
	{value:"<",text:"<"},
	{value:"<=",text:"<="},
	{value:"IS NULL",text:"为空"},
	{value:"IS NOT NULL",text:"不为空"}
  ]

/*选项*/
var OPTION_BUTTON = [{value:"",text:"请选择"},{value:0,text:"单选"},{value:1,text:"多选"}];