
Ext.define('BPSPortal.view.help.CustomerPicker', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.customerpicker', // 这个组件是选择产品的组件 
    emptyText: '',
    editable: false,
    matchFieldWidth: true,

    store: Ext.create('Ext.data.Store', {
        model: 'BPSPortal.model.CustProdLine',
        proxy: {
            type: 'direct',
            api: {
            	read: pricespecialdetailcontrol.readerCust
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
                  '<div data-qtip="custname:{custname}/prodname:{prodname}">{custname}/{prodname}</div>'
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

