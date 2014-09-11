BTStackExample
==============

A very simple example of how to use the BTStack java code along with a state machine created by the State Machine Compiler (http://smc.sourceforge.net/) to connect to a Polar H7 heart rate monitor.

Notice that you'll have to use the code in the DoubleDispatchEventHandling branch of the BTStack repository and the mojo built from the DoubleDispatchSupport branch of the BTStackBindingGenerator repository.

You will also have to download a copy of the State Machine Compiler from http://sourceforge.net/projects/smc/files/smc/6_3_0/ to compile any changes to the state machine as well as create a maven artifact for the smc runtime.

The file HeartRateConnectContext.java is created by running the command "java -jar <path_to_Smc.jar> -java -generic7 HeartRateConnect.sm" and moving the resulting file to the correct directory.  (Yes, that can be done more efficiently; please read the SMC documentation to see how.)

The HeartRateConnect implements the btstack EventHandler class and also contains the data and callbacks needed for the state machine to do any work.  The App class implements the PacketHandler interface and is what feeds the events into the HeartRateConnect class to drive the state machine.

I am currently not writing the configuration to start the notifications correctly.  I have done it using the bluez stack and the command line tools provided by bluez.
