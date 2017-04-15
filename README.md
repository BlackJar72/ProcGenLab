# ProcGenLab

A simple tool for testing, prototyping, and experimenting with procedural content generation.  It will allow any system which produces or can readily be reprented as a tile map or sort of time map to be tested visually, tweaked, and profiled independed 
a game (or similar).  Systems can be hacked in within the generators package in an IDE or written with the jar link as a 
dependency / library and then loaded into the gui at run time.

Actual documentation comming soon.

It's a small GUI framework for testing, prototyping, and experimenting with procedural map generation.  This way you can develop procedural maps making systems outside of an actual game, see the whole map, look at variety of maps quickly without having to spend time wandering around with cheats, and visualize things that might not be obvious in game (such as ideal paths through a level generated be passability and connectivity code).  It can also do some basic time profile and basic statistics on the time it takes to perform generation independent of a running game.

There isn't much documentation yet -- I hope to get that done soon(??).  Basically, generators extend IGenerator, and supply the information on the map(s) being created and return them and int[] arrays (int[][] technically) -- with generate() being called to generate the maps.

It can be used in two ways.  One is to open the whole thing in an IDE, hack generators into the generators packages and run from the IDE (these won't be visible in compiled jars).  I like this way because its quick and simple.  For those who want a more professional approach generators can be created separately with the compiled jar reference to use the API; these can them be loaded at run time.

It has several different palette types for different map types:

    DiscretePalette: For things like representing tiles, blocks, etc.
    ContinuousPalette: For things like height maps and climate maps
    DiscontinuousPalette: For maps with sudden translation, such and height maps with land and water
    LiteralPalette: A non-palette for complex uses where the colors might be defined in the generator

It currently exists only in source form -- but I assume anyone who would be interested in using it can edit and compile code.

This software licensed under a Creative Commons Attribution 4.0 International License: http://creativecommons.org/licenses/by/4.0/

