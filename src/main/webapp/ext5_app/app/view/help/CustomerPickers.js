
Ext.define('BPSPortal.view.help.CustomerPickers', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.customerpickers', // 这个组件是选择产品的组件 
    emptyText: '',
    editable: false,
    matchFieldWidth: true,

    store: Ext.create('Ext.data.Store', {
    	autoLoad: true,
        model: 'BPSPortal.model.ParentCustomer',
        proxy: {
            type: 'direct',
            api: {
            	read: customerControl.readerParentCustomer
    	    },
            reader: {
                type: 'json',
                rootProperty: 'records'
            },
            listeners: {
               // exception: app.ProxyException
            }
        }

    }),
    listConfig: {
        itemTpl: [
                  '<div data-qtip="custname:{custname}">{custname}</div>'
                ]
    },

    typeAhead: true,
    minChars: 1,
    queryMode: 'remote',
    queryParam: 'name',
    typeAhead: true,
    editable: true,
    queryDelay : 700
});

