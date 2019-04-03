
Ext.define('BPSPortal.view.help.DealerPicker', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.dealerpicker', // 这个组件是选择产品的组件 
    emptyText: '',
    editable: false,
    matchFieldWidth: true,

    store: Ext.create('Ext.data.Store', {
        model: 'BPSPortal.model.DealerInfos',
        proxy: {
            type: 'direct',
            api: {
            	read: dealerControl.getAll
    	    },
            reader: {
                type: 'json',
                rootProperty: 'records'
            }
        }

    }),
    listConfig: {
        itemTpl: [
                  '<div data-qtip="dealername:{dealername}">{dealername}</div>'
                ]
    },

    typeAhead: true,
    minChars: 1,
    queryMode: 'remote',
    queryParam: 'dealername',
    typeAhead: true,
    editable: true,
    queryDelay : 700
});

