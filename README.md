# Remote-Access-Tool-Simulator-
my old early attempt to emulate a Remote Access tool locally, mainly because im unsure of the legal or moral rules involved with putting an actual remote control access tool on github

in its current state the program has a creates a working serversocket, scanner, and printWriter, but takes all its commands through a Console class input, keeping everything local

my current use for the program is as practice for creating more local methods, and not remote ones

current methods:
resolve: returns This method grabs the IP address of the host and return it as a string InetAddress ip
Connect: connects a socket
Execute: executes the method sharing the name of the String arguemnt given

current commands: 
quit- quits the simulation

