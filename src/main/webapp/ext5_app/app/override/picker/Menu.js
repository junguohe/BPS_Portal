Ext.define('BPSPortal.override.picker.Menu', {
    override: 'Ext.menu.Menu',


    onBoxReady: function() {
        var me = this,
            listeners = {
                click: me.onClick,
                mouseover: me.onMouseOver,
                scope: me
            },
            iconSeparatorCls = me._iconSeparatorCls;


        if (Ext.supports.Touch) {
            listeners.pointerdown = me.onMouseOver;
        }


        me.focusableKeyNav.map.processEvent = function(e) {
            if (e.keyCode === e.ESC) {
                e.target = me.el.dom;
            }
            return e;
        };


        me.focusableKeyNav.map.addBinding([{
            key: 27,
            handler: me.onEscapeKey,
            scope: me
        }, {
            key: /[\w]/,
            handler: me.onShortcutKey,
            scope: me,
            shift: false,
            ctrl: false,
            alt: false
        }]);


        me.callParent(arguments);


        if (me.showSeparator) {
            me.iconSepEl = me.body.insertFirst({
                role: 'presentation',
                cls: iconSeparatorCls + ' ' + iconSeparatorCls + '-' + me.ui,
                html: '&#160;'
            });
        }


        me.mon(me.el, listeners);


        if (Ext.supports.MSPointerEvents || Ext.supports.PointerEvents) {
            me.mon(me.el, {
                scope: me,
                click: me.preventClick,
                translate: false
            });
        }


        me.mouseMonitor = me.el.monitorMouseLeave(100, me.onMouseLeave, me);
    },


    onFocusLeave: function(e) {
        var me = this;


        me.callSuper([e]);
        me.mixins.focusablecontainer.onFocusLeave.call(me, e);
        if (me.floating) {
            me.hide();
        }
    }
});