import Exeptions.Collision;

public class CollisionController {


    public static void testForCollision(String oldType, String newType) throws Collision {
        //System.out.println(oldType);
        if (oldType.equals(ElementType.PLAYER) && newType.equals(ElementType.CANDLEOFF)) {
            throw new Collision("light");
        }else if (oldType.equals(ElementType.WALL) && newType.equals(ElementType.FOOT)) {
            throw new Collision("wall");
        }else if (oldType.equals(ElementType.FLAME) && newType.equals(ElementType.PLAYER)) {
            throw new Collision("light");
        }else if (oldType.equals(ElementType.HOLE) && newType.equals(ElementType.FOOT)) {
            throw new Collision("fall");
        }else if (oldType.equals(ElementType.FOOT) && newType.equals(ElementType.HOLE)) {
            throw new Collision("fall");
        }else if (oldType.equals(ElementType.PLAYER) && (newType.equals(ElementType.CRATE) || newType.equals(ElementType.CANDLE))) {
            throw new Collision("hide");
        }
    }
}
