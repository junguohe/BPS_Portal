
Ext.define('BPSPortal.view.user.UpdateDealerWin', {
    extend: 'Ext.window.Window',
    alias: 'widget.updatedealerwin',

    requires: [
        'BPSPortal.view.user.UpdateDealerWinViewModel',
        'BPSPortal.view.user.UpdateDealerWinViewController'
    ],

    viewModel: {
        type: 'updatedealerwin'
    },
    
    controller: 'updatedealerwin',
    bodyPadding: 10,
    modal:true,
    closable: true,
    minHeight: 250,
    width: 420,
    title: '编辑',
    listeners: {
    	  afterrender: 'onAfterRender'
      },
      
    items:[
           {
               xtype: 'fieldcontainer',
               layout: 'column',
               padding: '10 10 10 20',
               fieldLabel: '',
               items: [
                   {
                	   xtype: 'textfield',
                	   fieldLabel: '经销商',
                	   name: 'dealername',
                	   reference:'dealername'
                   }
               ]
           },
           {
               xtype: 'fieldcontainer',
               layout: 'column',
               padding: '10 10 10 20',
               fieldLabel: '',
               items: [
                   {
                	   xtype: 'textfield',
                       fieldLabel: '经销商编码',
                       name: 'dealercode',
                       reference:'dealercode'
                   }
               ]
           },
           {
               xtype: 'fieldcontainer',
               layout: 'column',
               padding: '20 10 10 20',
               fieldLabel: '',
               items: [
					{
					    xtype: 'textfield',
					    fieldLabel: '经销商缩写',
					    name: 'userid',
					    hidden:true,
					    reference:'userids'
					},
                   {
                       xtype: 'textfield',
                       fieldLabel: '经销商缩写',
                       name: 'ename',
                       reference:'enames'
                   }
               ]
           },
           {
               xtype: 'fieldcontainer',
               layout: 'column',
               padding: '10 10 10 20',
               fieldLabel: '',
               items: [
                   {
                	   xtype: 'textfield',
                       fieldLabel: 'LoginName',
                       name: 'loginname',
                       reference:'loginname'
                   }
               ]
           },
           {
               xtype: 'fieldcontainer',
               layout: 'column',
               padding: '10 10 10 20',
               fieldLabel: '',
               items: [
                   {
                	   xtype: 'textfield',
                       fieldLabel: 'TelNo',
                       name: 'telno',
                       reference:'telno'
                   }
               ]
           },
           {
               xtype: 'fieldcontainer',
               layout: 'column',
               padding: '10 10 10 20',
               fieldLabel: '',
               items: [
                   {
                       xtype: 'textfield',
                       fieldLabel: '邮件',
                       name: 'email',
                       regex: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+(,([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4}))*$/,
                       regexText: "请输入正确的格式:xxx.xxx@xxx.xxx!各个邮件之间用英文逗号隔开",
                       reference:'emails'
                   }
               ]
           },
           {
               xtype: 'fieldcontainer',
               layout: 'column',
               padding: '10 10 10 20',
               fieldLabel: '',
               items: [
                   {
                       xtype: 'datefield',
                       fieldLabel: '失效时间',
                       name: 'expiredate',
                       reference:'expiredates'
                   }
               ]
           }
       
       ],
       dockedItems:[
                    {
                        xtype: 'toolbar',
                        dock: 'bottom',
                        items: [
                            {
                                xtype: 'button',
                                text: '确认',
                                listeners: {
                                    click: 'onSubmitClick'
                                }
                            }
                        ]
                    }
                ]


});