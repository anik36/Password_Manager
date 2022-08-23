

//Linear Probing Implementation
public class HashTablePassword implements HashTableMap{
    private final int useProbe;
    private Entry[] entries;
    private final float loadFactor;
    private int size, used;
    //used acquires space for NIL
    private final Entry NIL = new Entry(null, null);

    private static class Entry{
        Object key, value;
        Entry(Object k, Object v){
            key = k;
            value = v;
        }
    }
    public HashTablePassword(int capacity, float loadFactor, int useProbe){
        entries = new Entry[capacity];
        this.loadFactor = loadFactor;
        this.useProbe = useProbe;
    }

    //Complementary functions
    public int hash(Object key){
        return (key.hashCode() & 0x7FFFFFFF) % entries.length;
    }
    private int nextProbe(int h, int i){
        //Linear Probing
        return (h+i) % entries.length;
    }

    private void rehash(){
        Entry[] oldEntries = entries;
        entries = new Entry[2 * entries.length + 1];
        for (Entry entry : oldEntries) {
            if (entry == NIL || entry == null) continue;
            int h = hash(entry.key);
            for (int x = 0; x < entries.length; x++) {
                int j = nextProbe(h, x);
                if (entries[j] == null) {
                    entries[j] = entry;
                    break;
                }
            }
            used = size;
        }
    }

    @Override
    public Object getAccount(Object account) {
        int h = hash(account);
        for(int i = 0; i < entries.length; i++){
            int j = nextProbe(h , i);
            Entry entry = entries[j];
            if(entry == null)break;
            if(entry == NIL)continue;
            if(entry.key.equals(account)) return entry.value;
        }
        return null;
    }

    @Override
    public Object addAccount(Object account, Object password) {
        if(used > (loadFactor * entries.length))rehash();
        int h = hash(account);
        for (int i = 0; i < entries.length; i++){
            int j = (h+i) % entries.length;
            Entry entry = entries[j];
            if(entry==null){
                entries[j]= new Entry(account, password);
                ++size;
                ++used;
                return h;
            }
            if(entry == NIL)continue;
            if(entry.key.equals(account)){
                Object oldValue = entry.value;
                entries[j].value = password;
               // return (int) oldValue;
                return oldValue;
            }
        }
        return h;
    }

    @Override
    public Object removeAccount(Object account) {
        int h = hash(account);
        for(int i = 0; i < entries.length; i++){
            int j = nextProbe(h,i);
            Entry entry = entries[j];
            if(entry == NIL)continue;
            if(entry.key.equals(account)){
                Object Value = entry.value;
                entries[j] = NIL;
                size--;
                return Value;
            }
        }
        return null;
    }
}
