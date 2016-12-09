package kotik.simple.client.warcraftlogs.objects;

/**
 * Created by Roman_Kuznetcov on 09.12.2016.
 */
public enum Encounters {

    Nythendra(1853){},
    Ilgynoth(1873){},
    Elerethe_Renferal(1876){},
    Ursoc(1841){},
    Dragons(1854){},
    Cenarius(1877){},
    Xavius(1864){},
    Odyn(1958){},
    Guarm(1962){},
    Helya(2008){};


    private int value;

    private Encounters(int value) {
        this.value = value;
    }

    static public Encounters getType(int dType) {
        for (Encounters type: Encounters.values()) {
            if (type.getCode()==(dType)) {
                return type;
            }
        }
        throw new RuntimeException("unknown type");
    }

    static public Encounters getType(String dType) {
        for (Encounters type: Encounters.values()) {
            if (type.getCode()==(Integer.parseInt(dType))) {
                return type;
            }
        }
        throw new RuntimeException("unknown type");
    }

    public int getCode() {
        return value;
    }
}
