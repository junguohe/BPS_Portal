
Ext.define('BPSPortal.view.customer.customerupdate.UpdateWin', {
    extend: 'Ext.window.Window',
    alias: 'widget.updatewins',

    requires: [
		'BPSPortal.view.customer.customerupdate.CustomerUpdateViewModel',
        'BPSPortal.view.customer.customerupdate.CustomerUpdateViewController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Date',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.view.Table',
        'Ext.selection.CheckboxModel',
        'Ext.grid.column.Widget'
    ],

    viewModel: {
        type: 'customerupdate'
    },
    controller: 'customerupdate',
    bodyPadding: 10,
    modal:true,
    closable: true,
    height: 400,
    width: 1000,
    title: '批量转移',
    listeners: {
  	  afterrender: 'onAfterRenders'
    },
    items:[
           {
               xtype: 'gridpanel',
               reference:'customersGrid',
               bind:{
            	   store: '{customers}'
               },
               viewConfig:{
               	enableTextSelection:true
               },
               columns: [
                   {
                       xtype: 'gridcolumn',
                       dataIndex: 'prodname',
                       text: '产品线'
                   },
                   {
                       xtype: 'gridcolumn',
                       dataIndex: 'custcode',
                       width:80,
                       text: '客户编码'
                   },
                   {
                       xtype: 'gridcolumn',
                       dataIndex: 'custname',
                       width:230,
                       text: '客户名称'
                   },
                   {
                       xtype: 'gridcolumn',
                       dataIndex: 'dealername',
                       text: '原经销商'
                   },
                   {
                       xtype: 'gridcolumn',
                       dataIndex: 'bpssales',
                       text: '销售'
                   },
                   {
                       xtype: 'gridcolumn',
                       dataIndex: 'bpsfae',
                       text: 'FAE'
                   },
                   {
                       xtype: 'gridcolumn',
                       dataIndex: 'regstatus',
                       text: '客户状态',
                       renderer: function(value){
   	                    var result="";
   	                    if(value=="1"){
   	                        result="报备客户";
   	                    }else{
   	                        result="非报备客户";
   	                    }
   	                    return result;
   	                }
                   },
                   {
                       xtype: 'gridcolumn',
                       dataIndex: 'regstartdate',
                       width:150,
                       text: '报备生效日期',
                       renderer: function (val) {
                       	var result="";
                       	if (val != 'null'&& val!=null) {
                               result=val; 
                           }else{
                           	result="";
                           }              
                           return result;
                       }
                   },
                   {
                       xtype: 'widgetcolumn',
                       width:150,
                       text: '操作',
                       widget: {
                           xtype: 'button',
                           style: {
                           	background: 'white',
                           	border: 0
                           },
                           iconCls:'common-icon-delete',
                           text: '<font color ="black">删除</font>',
                           listeners: {
                               click: 'onDeleteClick'
                           }
                           	
                       }
                   }
               ],
               dockedItems: [
                   {
                       xtype: 'toolbar',
                       dock: 'top',
                       items: [
                           {
       						    xtype: 'dealerpicker',
       						    fieldLabel: '现经销商',
       						    displayField: 'dealername',
       						    valueField: 'dealerid',
       						    name: 'dealerid'
       						
       						},
       						 {
       						    xtype: 'personsalespicker',
       						    fieldLabel: '现BPS销售',
		                      	displayField: 'per_name',
		                      	valueField: 'empid',
       						    name: 'bpsid',
       						
       						},
       						 {
       						    xtype: 'personpicker',
       						    fieldLabel: '现FAE',
       						    emptyText: "请选择",
		                      	displayField: 'per_name',
		                      	valueField: 'empid',
       						    name: 'faeid'
       						
       						},
                           {
                               xtype: 'button',
                               width: 100,
                               text: '提交',
                               listeners: {
                                   click: 'onsubmitClick'
                               }
                           }
                       ]
                   }
               ]
           }
        ]


});