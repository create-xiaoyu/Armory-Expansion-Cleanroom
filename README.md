# CleanroomModTemplate
Mod development template for Cleanroom, uses Unimined

## Usage
Edit gradle.properties for general settings
If you don't need shadow plugin, remove it from build.gradle
If you want to change artifact file name, also change it in build.gradle
Mixin in Cleanroom only need 1~3 configs, MixinBooter API is deprecated, check mixin branch for more detail

**WARNING**: These features are missing:
- Applying dependencies AT. Since even JEI has many ATs, this mean you will have to test your mod in production launchers.
- Mixin in dev. Current approach rely on manifest file to get mixins, which won't work in runClient. You could still try to add them manually by calling Mixins.addConfiguration() though

## Branches
There are 3 branches available:
- main
- mixin
- scala

If you want to use non-main branches, after clicked *Create a new repository* under *Use this template*, check the *Include all branches* checkbox