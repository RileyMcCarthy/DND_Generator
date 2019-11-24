package mccarthr;

/**
*abstract class space.
**/
public abstract class Space implements java.io.Serializable{

  /**
  * abstract method.
  * @return description.
  **/
  public abstract  String getDescription();

  /**
  * abstract method.
  *  @param theDoor new door to add to space.
  **/
  public abstract void setDoor(Door theDoor);

}
