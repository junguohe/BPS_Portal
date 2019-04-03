
Ext.define('BPSPortal.view.price.pricetrial.PriceTrialDetail', {
	 extend: 'Ext.window.Window',
    alias: 'widget.pricetrialdetail',

    requires: [
		'BPSPortal.view.price.pricetrial.PriceTrialViewModel',
        'BPSPortal.view.price.pricetrial.PriceTrialViewController',
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
        type: 'pricetrial'
    },
    controller: 'pricetrial',
    bodyPadding: 10,
    height: 400,
    width: 850,
    closable: true,
    scrollable: true,
    title: '数据明细',
    listeners: {
  	  afterrender: 'onAfterRenders'
    },
    items:[
           {
               xtype: 'gridpanel',
               reference:'pricetrialdetailGrid',
               bind:{
            	   store: '{pricetrialdetail}'
               },
               renderTo: document.body,
               features: [{
                   ftype: 'summary'
               }],
          //     height:500,
               viewConfig:{
               	  enableTextSelection:true
               },
               plugins: [
                         {
                             ptype: 'cellediting',
                             clicksToEdit:1
                         }
                       ],
               columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'dealername',
                            text: '经销商'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'materialcode',
                            text: '产品编码'
                        },
                        {
        					xtype : 'gridcolumn',
        					dataIndex : 'materialname',
        					text : '产品名称'

        				}, 
                        {
        					xtype : 'numbercolumn',
        					dataIndex : 'quantity',
        					format:'0,000', 
                            align:'right',
        					text : '数量'
        				}, 
        				{
        					xtype : 'gridcolumn',
        					dataIndex : 'currency',
        					text : '币种'
        				}, 
                        {
                            xtype: 'numbercolumn',
                            dataIndex: 'dealerpriceinca',
                            format:'0,000.0000', 
                            align:'right',
                            text: ' 价格政策A经销商价格'

                        },
                        {
                            xtype: 'numbercolumn',
                            dataIndex: 'dealerpriceincb',
                            format:'0,000.0000', 
                            align:'right',
                            text: ' 价格政策B经销商价格'

                        },
                        {
                            xtype: 'numbercolumn',
                            dataIndex: 'diff',
                            format:'0,000.000', 
                            align:'right',
                            text: '差异 (B-A)*数量',
                            summaryType: 'sum',
                            summaryRenderer : function(value){
                            	return  '合计 :' + value;  
                            }
                            

                        }
                    ]
//               dockedItems: [
//                   {
//                       xtype: 'toolbar',
//                       dock: 'top',
//                       items: [
//                           {
//                               xtype: 'button',
//                               width: 100,
//                               text: '确定',
//                               listeners: {
//                                   click: 'onsubmitClick'
//                               }
//                           }
//                       ]
//                   }
//               ]
           }
        ]


});