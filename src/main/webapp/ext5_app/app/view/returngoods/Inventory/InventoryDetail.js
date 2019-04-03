
Ext.define('BPSPortal.view.returngoods.Inventory.InventoryDetail', {
	extend: 'Ext.form.Panel',
    alias: 'widget.inventorydetail',

    requires: [
		'BPSPortal.view.returngoods.Inventory.InventoryViewModel',
        'BPSPortal.view.returngoods.Inventory.InventoryViewController',
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
        type: 'Inventory'
    },
    controller: 'Inventory',
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
               reference:'uploadinventoryGrid',
               bind:{
            	   store: '{uploadinventory}'
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
                            xtype: 'numbercolumn',
                            dataIndex: 'qty',
                            format:'0,000', 
                            align:'right',
                            text: ' 数量'
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