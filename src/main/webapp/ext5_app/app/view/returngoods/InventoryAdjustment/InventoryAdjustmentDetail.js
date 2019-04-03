
Ext.define('BPSPortal.view.returngoods.InventoryAdjustment.InventoryAdjustmentDetail', {
    extend: 'Ext.form.Panel',
    alias: 'widget.inventoryadjustmentdetail',

    requires: [
		'BPSPortal.view.returngoods.InventoryAdjustment.InventoryAdjustmentViewModel',
        'BPSPortal.view.returngoods.InventoryAdjustment.InventoryAdjustmentViewController',
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
        type: 'InventoryAdjustment'
    },
    controller: 'InventoryAdjustment',
    bodyPadding: 10,
    closable: true,
    scrollable: true,
    title: '数据明细',
    listeners: {
  	  afterrender: 'onAfterRenders'
    },
    items:[
           {
               xtype: 'gridpanel',
               reference:'uploadinventoryadjustmentGrid',
               bind:{
            	   store: '{uploadinventoryadjustment}'
               },
               minHeight:550,
               viewConfig:{
               	  enableTextSelection:true
               },
               columns: [
         				{
        				    xtype: 'gridcolumn',
        				    dataIndex: 'errormsg',
        				    width:200,
        				    text: 'ErrorMsg'
        				},
        				{
        				    xtype: 'gridcolumn',
        				    dataIndex: 'id',
        				    hidden:true,
        				    text: '  id'
        				},
        				{
        				    xtype: 'gridcolumn',
        				    dataIndex: 'tid',
        				    hidden:true,
        				    text: '  tid'
        				},
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'dealername',
                            text: '  经销商'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'period',
                            text: '会计周期'
                        },
                        {
            					xtype : 'gridcolumn',
            					dataIndex : 'materialname',
            					width:200,
            					text : '产品名称'
            			},
        				{
        					xtype : 'gridcolumn',
        					dataIndex : 'materialcode',
        					text : '  产品编码'
        				}, 
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'refdealer',
                            text: '  相关经销商'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'adjtype',
                            text: '  类型',
                            renderer: function (val) {
                               var result = '';
                               if(val==1){
                            	   result='转入';
                               }else{
                            	   result='转出';
                               }       
                               return result;
                          
                          }
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'adjdate',
                            text: '日期',
                           renderer: function (val) {
                                var dt = new Date(val);
                                var update_date = Ext.Date.format(dt, 'Y-m-d')
                                if (val == "") {
                                    return "";
                                }          
                                return update_date;
                           
                           }
                        },
                        {
                            xtype: 'numbercolumn',
                            dataIndex: 'qty',
                            format:'0,000', 
                            align:'right',
                            text: ' 数量'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'remark',
                            text: '备注'
                        }
                    ],
               dockedItems: [
                   {
                       xtype: 'toolbar',
                       dock: 'top',
                       items: [
                           {
                               xtype: 'button',
                               width: 100,
                               text: '确定',
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