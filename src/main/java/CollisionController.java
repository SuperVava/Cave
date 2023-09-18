import Exeptions.Collision;

public class CollisionController {


    public static void testForCollision(int oldType, int newType) throws Collision {
        //System.out.println(oldType);
        if (oldType == ElementType.WALL && newType == ElementType.FOOT) {
            throw new Collision(ElementType.WALL);
        }
        if (oldType == ElementType.CANDLE && newType == ElementType.PLAYER) {
            throw new Collision(ElementType.CANDLE);
        }
        if (oldType == ElementType.HOLE && newType == ElementType.FOOT) {
            throw new Collision(ElementType.HOLE);
        }
    }
}
