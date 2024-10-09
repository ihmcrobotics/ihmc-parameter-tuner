# IHMC Parameter Tuner

This repository is designed to create a standalone application that connects to a yo variable server adn enables tuning of parameters. These parameters can then be exported to the corresponding xml file.

## Licensing

All of the software in *IHMC Parameter Tuner* is licensed under the Apache 2.0 license.

## Developing with *IHMC Parameter Tuner* from source

*IHMC Parameter Tuner* uses the [Gradle](https://gradle.org) build system, and requires JDK 17. 
We recommend working in IntelliJ.

Our developers are a mix of Windows and Linux users. We officially support:
- Windows 10/11
- Ubuntu 20.04+

Arch Linux will work fine for development.
Other GNU/Linux distros will likely work, however largely untested.
macOS is partially supported, but incomplete at this time.

To get set up, use our public Confluence pages:
https://ihmcrobotics.atlassian.net/wiki/spaces/PUBLIC/overview

## Other IHMC Libraries
IHMC Parameter Tuner both depends on and is depended on by many other IHMC Robotics Libraries. 
A small sampling of our other software includes:

- Euclid, an alternative vector/geometry library for Java with support for additional structures 
common in 3D geometry without needing vecmath or Java3D: https://github.com/ihmcrobotics/euclid
- IHMC YoVariables, our core data structures tools that enable the time-series tracing 
and analysis of Simulation Construction Set: https://github.com/ihmcrobotics/ihmc-yovariables

You can find all of our other repositories as well as the ones above at https://github.com/ihmcrobotics

## Building .jars
*IHMC Parameter Tuner* is pre-configured for generating Maven publications. 
You can publish directly from the source code right in to your local Maven
repository, e.g. the `$HOME/.m2` directory. These builds will be tagged with a 
build "version" of `"LOCAL"` instead of an incrementing version number.

An example workflow for developing against a local clone of the software:

1. Clone *IHMC Parameter Tuner*
2. Make modifications
3. Publish to your local `$HOME/.m2` repository

**To publish jars to your local Maven repository:**
```bash
$ cd /path/to/ihmc-parameter-tuner
$ gradle publishAll -PcompositeSearchHeight=0
```
## Maintainers

* Robert Griffin (rgriffin@ihmc.org)
* Dexton Anderson (danderson@ihmc.org)
