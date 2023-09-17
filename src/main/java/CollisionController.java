import Exeptions.Collision;

public class CollisionController {


    public static void testForCollision(int oldType, int newType) throws Collision {
        if (oldType == ElementType.WALL && newType == ElementType.WALL) {
            throw new Collision(ElementType.WALL);
        }
    }
}
