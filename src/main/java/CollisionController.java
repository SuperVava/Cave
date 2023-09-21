import Exeptions.Collision;

public class CollisionController {


    public static void testForCollision(String oldType, String newType) throws Collision {
        //System.out.println(oldType);
        if (oldType.equals(ElementType.PLAYER) && newType.equals(ElementType.CANDLEOFF)) {
            throw new Collision("light");
        }else if ((oldType.equals(ElementType.WALL) || oldType.equals(ElementType.CRATE)) && newType.equals(ElementType.FOOT)) {
            throw new Collision("wall");
        }else if (oldType.equals(ElementType.FLAME) && newType.equals(ElementType.PLAYER)) {
            throw new Collision("light");
        }else if (oldType.equals(ElementType.HOLE) && newType.equals(ElementType.FOOT)) {
            throw new Collision("fall");
        }else if (oldType.equals(ElementType.FOOT) && newType.equals(ElementType.HOLE)) {
            throw new Collision("fall");
        }else if (oldType.equals(ElementType.ENTITY_LOW) && newType.equals(ElementType.HIDE)) {
            throw new Collision("hide");
        }else if (oldType.equals(ElementType.CRATE) && newType.equals(ElementType.HIDE)) {
            throw new Collision("stacked");
        }else if (oldType.equals(ElementType.PLAYER) && newType.equals(ElementType.EXIT)) {
            throw new Collision("finish");
        }else if(oldType.equals(ElementType.CRATE) && newType.equals(ElementType.ENTITY_LOW)){
            throw new Collision("canHideLow");
        }else if(oldType.equals(ElementType.PLAYER) && newType.equals(ElementType.WOLF)){
            throw new Collision("kill");
        }
    }
}
