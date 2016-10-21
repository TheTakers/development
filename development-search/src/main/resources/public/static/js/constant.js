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
var OPTION_MULTIPLE = [{value:"",text:"请选择"},{value:0,text:"单选"},{value:1,text:"多选"}];


/*表达式*/
var WIN_SIZE = [
    {value:"",text:"请选择"},          
    {value:"40",text:"40%"},
    {value:"50",text:"50%"},
    {value:"60",text:"60%"},
    {value:"70",text:"70%"},
	{value:"80",text:"80%"},
	{value:"90",text:"90%"},
	{value:"100",text:"100%"}
  ]

var RULE_DATA = [
 {value:"",text:"请选择"},          
 {value:"1",text:"40%"},
 {value:"2",text:"50%"},
 {value:"3",text:"60%"},
 {value:"4",text:"70%"},
 {value:"5",text:"80%"},
 {value:"6",text:"90%"},
 {value:"7",text:"100%"}             
]