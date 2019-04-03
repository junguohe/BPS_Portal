
Ext.define('BPSPortal.view.user.PassWordWin', {
    extend: 'Ext.window.Window',
    alias: 'widget.PassWordWin',

    requires: [
               'BPSPortal.view.user.PassWordWinViewController',
               'Ext.form.Panel',
               'Ext.toolbar.Toolbar',
               'Ext.button.Button',
               'Ext.form.field.ComboBox',
               'Ext.form.field.Date',
               'Ext.form.RadioGroup',
               'Ext.form.field.Radio'
           ],
           controller: 'PassWordWin',
           bodyPadding: 10,
           modal:true,
           closable: true,
           height: 300,
           width: 400,
           title: '修改密码',
           items:[
                  		{
                        xtype: 'textfield',
                        fieldLabel: '原始密码',
                        name:'password',
                        inputType: 'password',
                        padding:'20 0 0 0',
                        blankText : '原始密码不能为空'                            
                    },
                    { 
                        xtype: 'textfield',
                        fieldLabel: '新密码',
                        padding:'20 0 0 0',
                        inputType: 'password',
                        name:'newpassword',
                        blankText : '新密码不能为空',                            
                    },
                    {
                        xtype: 'textfield',
                        fieldLabel: '确认密码',
                        padding:'20 0 0 0',
                        inputType: 'password',
                        name: 'confirmpassword',
                        blankText : '确认密码不能为空',
                   }
       		],
                   dockedItems: [
                         {
                             xtype: 'toolbar',
                             dock: 'bottom',
                             items: [
                                 {
                                     xtype: 'tbfill'
                                 },
                                 {
                                   xtype: 'button',
                                   text: '确定',
                                   listeners: {
                                       click: 'onSaveParamClick'
                                   }
                               },
                               {
                                   xtype: 'button',
                                   text: '取消',
                                   listeners: {
                                       click: 'onCancelClick'
                                   }
                               }
                             ]
                         }
                     ]


});