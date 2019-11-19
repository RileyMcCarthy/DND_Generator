package mccarthr;

//java -cp build;lib/dnd-20190914.jar mccarthr.Main

/**
*main class.
**/
class Main {

/**
* main function.
*@param args args for main.
*/
  public static void main(final String[] args) {
    Level level = new Level();
    level.generateLevel();
    level.printDescription();
  }
  public void test() {
    Level level = new Level();
  }

}
