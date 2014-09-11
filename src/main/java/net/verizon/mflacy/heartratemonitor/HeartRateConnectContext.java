/*
 * ex: set ro:
 * DO NOT EDIT.
 * generated by smc (http://smc.sourceforge.net/)
 * from file : HeartRateConnect.sm
 */


// FSM for heart rate monitor connection(s).


package net.verizon.mflacy.heartratemonitor;

import com.bluekitchen.btstack.event.*;

public class HeartRateConnectContext
    extends statemap.FSMContext
{
//---------------------------------------------------------------
// Member methods.
//

    public HeartRateConnectContext(HeartRateConnect owner)
    {
        this (owner, HeartRateConnectFSM.Init);
    }

    public HeartRateConnectContext(HeartRateConnect owner, HeartRateConnectState initState)
    {
        super (initState);

        _owner = owner;
    }

    @Override
    public void enterStartState()
    {
        getState().entry(this);
        return;
    }

    public void handleBTstackEventState(BTstackEventState event)
    {
        _transition = "handleBTstackEventState";
        getState().handleBTstackEventState(this, event);
        _transition = "";
        return;
    }

    public void handleGAPLEAdvertisingReport(GAPLEAdvertisingReport event)
    {
        _transition = "handleGAPLEAdvertisingReport";
        getState().handleGAPLEAdvertisingReport(this, event);
        _transition = "";
        return;
    }

    public void handleGATTAllCharacteristicDescriptorsQueryResult(GATTAllCharacteristicDescriptorsQueryResult event)
    {
        _transition = "handleGATTAllCharacteristicDescriptorsQueryResult";
        getState().handleGATTAllCharacteristicDescriptorsQueryResult(this, event);
        _transition = "";
        return;
    }

    public void handleGATTCharacteristicQueryResult(GATTCharacteristicQueryResult event)
    {
        _transition = "handleGATTCharacteristicQueryResult";
        getState().handleGATTCharacteristicQueryResult(this, event);
        _transition = "";
        return;
    }

    public void handleGATTNotification(GATTNotification event)
    {
        _transition = "handleGATTNotification";
        getState().handleGATTNotification(this, event);
        _transition = "";
        return;
    }

    public void handleGATTQueryComplete(GATTQueryComplete event)
    {
        _transition = "handleGATTQueryComplete";
        getState().handleGATTQueryComplete(this, event);
        _transition = "";
        return;
    }

    public void handleGATTServiceQueryResult(GATTServiceQueryResult event)
    {
        _transition = "handleGATTServiceQueryResult";
        getState().handleGATTServiceQueryResult(this, event);
        _transition = "";
        return;
    }

    public void handleHCIEventLEConnectionComplete(HCIEventLEConnectionComplete event)
    {
        _transition = "handleHCIEventLEConnectionComplete";
        getState().handleHCIEventLEConnectionComplete(this, event);
        _transition = "";
        return;
    }

    public HeartRateConnectState getState()
        throws statemap.StateUndefinedException
    {
        if (_state == null)
        {
            throw(
                new statemap.StateUndefinedException());
        }

        return ((HeartRateConnectState) _state);
    }

    protected HeartRateConnect getOwner()
    {
        return (_owner);
    }

    public void setOwner(HeartRateConnect owner)
    {
        if (owner == null)
        {
            throw (
                new NullPointerException(
                    "null owner"));
        }
        else
        {
            _owner = owner;
        }

        return;
    }

//---------------------------------------------------------------
// Member data.
//

    transient private HeartRateConnect _owner;

    //-----------------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;

//---------------------------------------------------------------
// Inner classes.
//

    public static abstract class HeartRateConnectState
        extends statemap.State
    {
    //-----------------------------------------------------------
    // Member methods.
    //

        protected HeartRateConnectState(String name, int id)
        {
            super (name, id);
        }

        protected void entry(HeartRateConnectContext context) {}
        protected void exit(HeartRateConnectContext context) {}

        protected void handleBTstackEventState(HeartRateConnectContext context, BTstackEventState event)
        {
            Default(context);
        }

        protected void handleGAPLEAdvertisingReport(HeartRateConnectContext context, GAPLEAdvertisingReport event)
        {
            Default(context);
        }

        protected void handleGATTAllCharacteristicDescriptorsQueryResult(HeartRateConnectContext context, GATTAllCharacteristicDescriptorsQueryResult event)
        {
            Default(context);
        }

        protected void handleGATTCharacteristicQueryResult(HeartRateConnectContext context, GATTCharacteristicQueryResult event)
        {
            Default(context);
        }

        protected void handleGATTNotification(HeartRateConnectContext context, GATTNotification event)
        {
            Default(context);
        }

        protected void handleGATTQueryComplete(HeartRateConnectContext context, GATTQueryComplete event)
        {
            Default(context);
        }

        protected void handleGATTServiceQueryResult(HeartRateConnectContext context, GATTServiceQueryResult event)
        {
            Default(context);
        }

        protected void handleHCIEventLEConnectionComplete(HeartRateConnectContext context, HCIEventLEConnectionComplete event)
        {
            Default(context);
        }

        protected void Default(HeartRateConnectContext context)
        {
            throw (
                new statemap.TransitionUndefinedException(
                    "State: " +
                    context.getState().getName() +
                    ", Transition: " +
                    context.getTransition()));
        }

    //-----------------------------------------------------------
    // Member data.
    //
    }

    /* package */ static abstract class HeartRateConnectFSM
    {
    //-----------------------------------------------------------
    // Member methods.
    //

    //-----------------------------------------------------------
    // Member data.
    //

        //-------------------------------------------------------
        // Constants.
        //

        public static final HeartRateConnectFSM_Init Init =
            new HeartRateConnectFSM_Init("HeartRateConnectFSM.Init", 0);
        public static final HeartRateConnectFSM_LEScanRunning LEScanRunning =
            new HeartRateConnectFSM_LEScanRunning("HeartRateConnectFSM.LEScanRunning", 1);
        public static final HeartRateConnectFSM_LEConnecting LEConnecting =
            new HeartRateConnectFSM_LEConnecting("HeartRateConnectFSM.LEConnecting", 2);
        public static final HeartRateConnectFSM_DiscoveringPrimaryServices DiscoveringPrimaryServices =
            new HeartRateConnectFSM_DiscoveringPrimaryServices("HeartRateConnectFSM.DiscoveringPrimaryServices", 3);
        public static final HeartRateConnectFSM_DiscoveringServiceCharacteristics DiscoveringServiceCharacteristics =
            new HeartRateConnectFSM_DiscoveringServiceCharacteristics("HeartRateConnectFSM.DiscoveringServiceCharacteristics", 4);
        public static final HeartRateConnectFSM_DiscoveringCharacteristicDescriptors DiscoveringCharacteristicDescriptors =
            new HeartRateConnectFSM_DiscoveringCharacteristicDescriptors("HeartRateConnectFSM.DiscoveringCharacteristicDescriptors", 5);
        public static final HeartRateConnectFSM_Running Running =
            new HeartRateConnectFSM_Running("HeartRateConnectFSM.Running", 6);
    }

    protected static class HeartRateConnectFSM_Default
        extends HeartRateConnectState
    {
    //-----------------------------------------------------------
    // Member methods.
    //

        protected HeartRateConnectFSM_Default(String name, int id)
        {
            super (name, id);
        }

    //-----------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class HeartRateConnectFSM_Init
        extends HeartRateConnectFSM_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private HeartRateConnectFSM_Init(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void handleBTstackEventState(HeartRateConnectContext context, BTstackEventState event)
        {
            HeartRateConnect ctxt = context.getOwner();

            if ( ctxt.isUp(event) )
            {
                (context.getState()).exit(context);
                context.clearState();
                try
                {
                    ctxt.startLEScan();
                }
                finally
                {
                    context.setState(HeartRateConnectFSM.LEScanRunning);
                    (context.getState()).entry(context);
                }

            }
            else if ( !ctxt.isUp(event) )
            {
                // No actions.
            }            else
            {
                super.handleBTstackEventState(context, event);
            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class HeartRateConnectFSM_LEScanRunning
        extends HeartRateConnectFSM_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private HeartRateConnectFSM_LEScanRunning(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void handleGAPLEAdvertisingReport(HeartRateConnectContext context, GAPLEAdvertisingReport event)
        {
            HeartRateConnect ctxt = context.getOwner();

            (context.getState()).exit(context);
            context.clearState();
            try
            {
                ctxt.stopLEScan();
                ctxt.recordAddrInfo(event);
                ctxt.performLEConnect();
            }
            finally
            {
                context.setState(HeartRateConnectFSM.LEConnecting);
                (context.getState()).entry(context);
            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class HeartRateConnectFSM_LEConnecting
        extends HeartRateConnectFSM_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private HeartRateConnectFSM_LEConnecting(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void handleGAPLEAdvertisingReport(HeartRateConnectContext context, GAPLEAdvertisingReport event)
        {
            HeartRateConnect ctxt = context.getOwner();

            HeartRateConnectState endState = context.getState();
            context.clearState();
            try
            {
                ctxt.whine(event);
            }
            finally
            {
                context.setState(endState);
            }

            return;
        }

        @Override
        protected void handleHCIEventLEConnectionComplete(HeartRateConnectContext context, HCIEventLEConnectionComplete event)
        {
            HeartRateConnect ctxt = context.getOwner();

            (context.getState()).exit(context);
            context.clearState();
            try
            {
                ctxt.recordConnectionHandle(event);
                ctxt.discoverPrimaryServices();
            }
            finally
            {
                context.setState(HeartRateConnectFSM.DiscoveringPrimaryServices);
                (context.getState()).entry(context);
            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class HeartRateConnectFSM_DiscoveringPrimaryServices
        extends HeartRateConnectFSM_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private HeartRateConnectFSM_DiscoveringPrimaryServices(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void handleGATTQueryComplete(HeartRateConnectContext context, GATTQueryComplete event)
        {
            HeartRateConnect ctxt = context.getOwner();

            if ( ctxt.haveServices() )
            {
                (context.getState()).exit(context);
                context.clearState();
                try
                {
                    ctxt.discoverNextServiceCharacteristics();
                }
                finally
                {
                    context.setState(HeartRateConnectFSM.DiscoveringServiceCharacteristics);
                    (context.getState()).entry(context);
                }

            }
            else
            {
                super.handleGATTQueryComplete(context, event);
            }

            return;
        }

        @Override
        protected void handleGATTServiceQueryResult(HeartRateConnectContext context, GATTServiceQueryResult event)
        {
            HeartRateConnect ctxt = context.getOwner();

            HeartRateConnectState endState = context.getState();
            context.clearState();
            try
            {
                ctxt.recordPrimaryService(event);
            }
            finally
            {
                context.setState(endState);
            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class HeartRateConnectFSM_DiscoveringServiceCharacteristics
        extends HeartRateConnectFSM_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private HeartRateConnectFSM_DiscoveringServiceCharacteristics(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void handleGATTCharacteristicQueryResult(HeartRateConnectContext context, GATTCharacteristicQueryResult event)
        {
            HeartRateConnect ctxt = context.getOwner();

            HeartRateConnectState endState = context.getState();
            context.clearState();
            try
            {
                ctxt.recordServiceCharacteristic(event);
            }
            finally
            {
                context.setState(endState);
            }

            return;
        }

        @Override
        protected void handleGATTQueryComplete(HeartRateConnectContext context, GATTQueryComplete event)
        {
            HeartRateConnect ctxt = context.getOwner();

            if ( ctxt.haveMoreServices() )
            {
                HeartRateConnectState endState = context.getState();
                context.clearState();
                try
                {
                    ctxt.discoverNextServiceCharacteristics();
                }
                finally
                {
                    context.setState(endState);
                }

            }
            else if ( !ctxt.haveMoreServices() )
            {
                (context.getState()).exit(context);
                context.clearState();
                try
                {
                    ctxt.discoverFirstServiceCharacteristicDescriptor();
                }
                finally
                {
                    context.setState(HeartRateConnectFSM.DiscoveringCharacteristicDescriptors);
                    (context.getState()).entry(context);
                }

            }            else
            {
                super.handleGATTQueryComplete(context, event);
            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class HeartRateConnectFSM_DiscoveringCharacteristicDescriptors
        extends HeartRateConnectFSM_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private HeartRateConnectFSM_DiscoveringCharacteristicDescriptors(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void handleGATTAllCharacteristicDescriptorsQueryResult(HeartRateConnectContext context, GATTAllCharacteristicDescriptorsQueryResult event)
        {
            HeartRateConnect ctxt = context.getOwner();

            HeartRateConnectState endState = context.getState();
            context.clearState();
            try
            {
                ctxt.recordCharacteristicDescriptor(event);
            }
            finally
            {
                context.setState(endState);
            }

            return;
        }

        @Override
        protected void handleGATTCharacteristicQueryResult(HeartRateConnectContext context, GATTCharacteristicQueryResult event)
        {
            HeartRateConnect ctxt = context.getOwner();

            HeartRateConnectState endState = context.getState();
            context.clearState();
            try
            {
                ctxt.whine(event);
            }
            finally
            {
                context.setState(endState);
            }

            return;
        }

        @Override
        protected void handleGATTQueryComplete(HeartRateConnectContext context, GATTQueryComplete event)
        {
            HeartRateConnect ctxt = context.getOwner();

            if ( ctxt.haveMoreCharacteristics() )
            {
                HeartRateConnectState endState = context.getState();
                context.clearState();
                try
                {
                    ctxt.discoverNextServiceCharacteristicDescriptor();
                }
                finally
                {
                    context.setState(endState);
                }

            }
            else if ( !ctxt.haveMoreCharacteristics() )
            {
                (context.getState()).exit(context);
                context.clearState();
                try
                {
                    ctxt.registerForNotifications();
                }
                finally
                {
                    context.setState(HeartRateConnectFSM.Running);
                    (context.getState()).entry(context);
                }

            }            else
            {
                super.handleGATTQueryComplete(context, event);
            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class HeartRateConnectFSM_Running
        extends HeartRateConnectFSM_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private HeartRateConnectFSM_Running(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void handleGATTNotification(HeartRateConnectContext context, GATTNotification event)
        {
            HeartRateConnect ctxt = context.getOwner();

            HeartRateConnectState endState = context.getState();
            context.clearState();
            try
            {
                ctxt.recordNotification(event);
            }
            finally
            {
                context.setState(endState);
            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }
}

/*
 * Local variables:
 *  buffer-read-only: t
 * End:
 */