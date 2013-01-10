# Skuttande Nyan Cat!

Author: Daniel "MaTachi" Jonsson
Tools: Eclipse, GIMP, Aseprite, MyPaint
Development platform: Ubuntu 12.10
Libraries: LibGDX, Artemis

## Texture packer

    rm skuttande-nyan-cat-android/assets/sprites/sprites.{atlas,png} ; java -classpath skuttande-nyan-cat/libs/gdx.jar:skuttande-nyan-cat/libs/gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 sprites skuttande-nyan-cat-android/assets/sprites sprites
