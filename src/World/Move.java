package World;

public class Move {
    public enum MoveDirection{
        N,
        NW,
        W,
        SW,
        S,
        SE,
        E,
        NE,
        P;
    }

    public  String toString2(Enum arg) {
        if (MoveDirection.N.equals(arg)) {
            return "PÓŁNOC";
        } else if (MoveDirection.NW.equals(arg)) {
            return "PÓŁNOCNY - ZACHÓD";
        } else if (MoveDirection.W.equals(arg)) {
            return "ZACHÓD";
        } else if (MoveDirection.SE.equals(arg)) {
            return "POŁUDNIOWY - ZACHÓD";
        } else if (MoveDirection.S.equals(arg)) {
            return "POŁUDNIE";
        } else if (MoveDirection.SE.equals(arg)) {
            return "POŁUDNIOWY - WSCHÓD";
        } else if (MoveDirection.E.equals(arg)) {
            return "WSCHÓD";
        }else if (MoveDirection.NE.equals(arg)) {
            return "PÓŁNOCNY - WSCHÓD";
        } else if (MoveDirection.P.equals(arg)) {
            return "BRAK RUCHU";
        }
        return null;
    }

    public static Enum next(MoveDirection arg) {
        Move.MoveDirection agr1 = MoveDirection.P;
        if (MoveDirection.N.equals(arg)) {
            agr1 = MoveDirection.NW;
            return agr1;
        } else if (MoveDirection.NW.equals(arg)) {
            agr1 = MoveDirection.W;
            return agr1;
        } else if (MoveDirection.W.equals(arg)) {
            agr1 = MoveDirection.SW;
            return agr1;
        } else if (MoveDirection.SW.equals(arg)) {
            agr1 = MoveDirection.S;
            return agr1;
        }else if (MoveDirection.S.equals(arg)) {
            agr1 = MoveDirection.SE;
            return agr1;
        }else if (MoveDirection.SE.equals(arg)) {
            agr1 = MoveDirection.E;
            return agr1;
        }else if (MoveDirection.E.equals(arg)) {
            agr1 = MoveDirection.NE;
            return agr1;
        }else if (MoveDirection.NE.equals(arg)) {
            agr1 = MoveDirection.N;
            return agr1;
        }
        return agr1;
    }

    public Enum previous(Enum arg){
        MoveDirection agr1 = MoveDirection.P;
        if (MoveDirection.N.equals(arg)) {
            agr1 = MoveDirection.NE;
            return agr1;
        } else if (MoveDirection.NE.equals(arg)) {
            agr1 = MoveDirection.E;
            return agr1;
        } else if (MoveDirection.E.equals(arg)) {
            agr1 = MoveDirection.SE;
            return agr1;
        } else if (MoveDirection.SE.equals(arg)) {
            agr1 = MoveDirection.S;
            return agr1;
        }else if (MoveDirection.S.equals(arg)) {
            agr1 = MoveDirection.SW;
            return agr1;
        }else if (MoveDirection.SW.equals(arg)) {
            agr1 = MoveDirection.W;
            return agr1;
        }else if (MoveDirection.W.equals(arg)) {
            agr1 = MoveDirection.NW;
            return agr1;
        }else if (MoveDirection.NW.equals(arg)) {
            agr1 = MoveDirection.N;
            return agr1;
        }
        return agr1;
    }

    public static Vector2d toUnitVector(Enum arg){
        int a = 0, b = 0;
        if (MoveDirection.N.equals(arg)) {
            a = 0;
            b = 1;

        } else if (MoveDirection.NW.equals(arg)) {
            a = -1;
            b = 1;

        } else if (MoveDirection.W.equals(arg)) {
            a = -1;
            b = 0;

        } else if (MoveDirection.SW.equals(arg)) {
            a = -1;
            b = -1;

        } else if (MoveDirection.S.equals(arg)) {
            a = 0;
            b = -1;

        } else if (MoveDirection.SE.equals(arg)) {
            a = 1;
            b = -1;

        } else if (MoveDirection.E.equals(arg)) {
            a = 1;
            b = 0;

        } else if (MoveDirection.NE.equals(arg)) {
            a = 1;
            b = 1;

        }
        Vector2d newvector = new Vector2d(a, b);
        return newvector;

    }

    public Enum whichDirection(Enum arg, int turn) {
        while (turn != 0) {
            arg = next((MoveDirection) arg);
            turn -= 1;
        }
        return arg;
    }

    public static Move.MoveDirection turn(Move.MoveDirection arg, int a) {
        while(a > 0){
            a -= 1;
            arg = (Move.MoveDirection) Move.next(arg);
        }
        return arg;
    }
}
