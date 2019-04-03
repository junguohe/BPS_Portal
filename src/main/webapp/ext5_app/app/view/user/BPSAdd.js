
Ext.define('BPSPortal.view.user.BPSAdd', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.bpsadd',

    requires: [
        'BPSPortal.view.user.BPSAddViewModel',
        'BPSPortal.view.user.BPSAddViewController', 
        'Ext.form.field.ComboBox',
        'Ext.tab.Panel',
        'Ext.tab.Tab',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.view.Table',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill'
    ],

    viewModel: {
        type: 'bpsadd'
    },
    layout: {
        type: 'hbox',
        align: 'stretch'
    },
    controller: 'bpsadd',
    title: 'BPS用户管理',
    closable: true,
    scrollable: true,
    items: [{
        xtype: 'container',
        flex: 1,
        layout: {
            type: 'vbox',
            align: 'stretch'
        },
        items: [
            {
                xtype: 'form',
                bodyPadding: 10,
                reference: 'toolingSerachForm',
                title: '',
                items: [
                    {
                        xtype: 'fieldcontainer',
                        layout: 'column',
                        fieldLabel: '',
                        items: [
                            {
                                xtype: 'textfield',
                                fieldLabel: '用户名',
                                name: 'username',
                            }
                            
                        ]
                    }
                ],

                dockedItems: [
                      {
                          xtype: 'toolbar',
                          dock: 'bottom',
                          items: [
                              {
                                  xtype: 'button',
                                  text: '查询',
                                  itemId: 'searchBtn',
                                  iconCls: 'common-icon-serach',
                                  listeners: {
                                      click: 'onSearchClick'
                                  }
                              }
                          ]
                      }
                  ]
            },
            {
                xtype: 'gridpanel',
                title:'查询结果',
                flex: 1,
                reference: 'BPSGrid',
                viewConfig: {
                    enableTextSelection: true
                },
                bind:{
                	store:'{bpsinfo}'
                },
                columns: [
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'personalid',
                          width:200,
                          text: 'PersonalID',
                         
                      },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'personalname',
                        width:200,
                        text: '用户名',
                       
                    },
                    {
                        xtype: 'gridcolumn',
                        width:200,
                        dataIndex: 'adaccount',
                        text: 'ADAccount'
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'personalemail',
                        width:300,
                        text: 'Email'
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'deptname',
                        width:200,
                        text: 'DeptName'
                    },
                    {
                        xtype: 'gridcolumn',
                        width:200,
                        dataIndex: 'hiredate',
                        text: 'HireDate'
                    }

                ],
                dockedItems: [
                      {
                          xtype: 'pagingtoolbar',
                          dock: 'bottom',
                          bind: {
      				        store: '{bpsinfo}'
      				      },
                          displayInfo: true,
                          displayMsg: 'Displaying data {0} - {1} of {2}'
                      },
                      {
                          xtype: 'toolbar',
                          dock: 'top',
                          items: [
                              {
                                  xtype: 'button',
                                  text: '修改权限',
                                  iconCls: 'common_icon_update',
                                  listeners: {
                                      click: 'onUpdateClick'
                                  }
                              }
                          ]
                      }
                   ]
            	}
            ]
    	}
    ]
});