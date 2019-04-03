
Ext.define('BPSPortal.view.help.CityPicker', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.citypicker', // 这个组件是选择产品的组件 
    emptyText: '',
    editable: false,
    matchFieldWidth: true,

    store: Ext.create('Ext.data.Store', {
        model: 'BPSPortal.model.Region',
        proxy: {
            type: 'direct',
            api: {
    	    	read: regioninfocontrol.findpro
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
                  '<div data-qtip="city:{city}">{city}</div>'
                ]
    },

    typeAhead: true,
    minChars: 1,
    queryMode: 'remote',
    queryParam: 'city',
    typeAhead: true,
    editable: true,
    queryDelay : 700
});
