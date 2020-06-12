Enjoy this two player tank game!

## IDE Used : IntelliJ IDEA 2018.3.5
## Java Version : 1.8.0_201

## TODO
  - If not running through jar file:
    Certain file locations need to be replaced, all marked with TODO in source code

## Running the game :
  - Running through Jar (Currently set up for this)
    - Double click the jar file, files will be pulled from the \resources folder in the same directory as the jar
  - Running through IntelliJ (Have to uncomment code)
    - In the TRE.java, Tank.java, and TileLayer.java class, you need to comment out
      the code that pulls the resources from the resources folder and uncomment
      the code that allows you to pull resources from your local directory,
      you MUST set this to your directory
    - Run the TRE.java file either through IntelliJ or "javac *.java" in your command line, then "java TRE"
## Controls:
  - Tank 1 (Left Screen):

        Up    :   W

        Down  :   S

        Left  :   A

        Right :   D

        Shoot :   Spacebar

  - Tank 2 (Right Screen):

        Up    :   Up Arrow Key

        Down  :   Down Arrow Key

        Left  :   Left Arrow Key

        Right :   Right Arrow Key

        Shoot :   Enter (Both Enter Key Above Shift and Number Pad Enter Work)
