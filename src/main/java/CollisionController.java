import Exeptions.Collision;

public class CollisionController {


    public static void testForCollision(String oldType, String newType) throws Collision {
        System.out.println(oldType);
        if (oldType.equals(ElementType.WALL) && newType.equals(ElementType.FOOT)) {
            throw new Collision(ElementType.WALL);
        }
        if (oldType.equals(ElementType.CANDLE) && newType.equals(ElementType.PLAYER)) {
            throw new Collision(ElementType.CANDLE);
        }
        if (oldType.equals(ElementType.HOLE) && newType.equals(ElementType.FOOT)) {
            throw new Collision(ElementType.HOLE);
        }
        if (oldType.equals(ElementType.FOOT) && newType.equals(ElementType.HOLE)) {
            throw new Collision(ElementType.FOOT);
        }
    }
}
