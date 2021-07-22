package Odevler;

import java.util.ArrayList;
/**
 * <h1>Telefon Rehberi</h1>
 * Bu java programı 20 kişilik telefon rehberini içermektedir.
 * Hash yapısı, hazır java kütüphanelerinden yararlanılmadan oluşturulmuştur.
 * <p>
 *     Hash yapısı ve list kullanılmıştır.
 *     Aynı baş harfe sahip isimler hash yapısında peş peşe,list olarak tutulmaktadır.
 *     Böylece arama daha kolay gerçekleşebilmektedir.
 * </p>
 * <b>Note:</b> 20 kişinin telefon numaraları rasgele atanmıştır.
 * 20 kişiden oluşan telefon rehberinde; 5 tane N, , 5 tane C, 5 tane E, 5 tane M harfi ile başlayan isim vardır.
 * Yani hash yapısının boyutu 4 tür. Aynı harfle başlayanlar list de arka arkaya dizilmiştir.
 */
class People2 {
    String Name;
    String Phone;
    People2(String Phone,String Name ){
        this.Phone=Phone;
        this.Name=Name;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }

}
class HashNode<K, V> {
    K key;
    V value;
    HashNode<K, V> next;//bir sonraki node u göserecek, referanstır.
    // Kurucu metot
    public HashNode(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
// Tüm hash tablosunu temsil edecek sınıf
class Map<K, V> {
    private ArrayList<HashNode<K, V> > bucketArray;// bucketArray, diziyi depolamak için kullanılır
    private int numBuckets; // Arraylist in max kapasitesi
    private int size;//arraylist in içerdiği eleman sayısı(boyut)
    //Kurucu metot.Arraylist oluşturur. Kapasite, boyut degerlerini ayarlar.
    public Map()
    {
        bucketArray = new ArrayList<>();
        numBuckets = 10;
        size = 0;
        // En başta içi bomboş
        for (int i = 0; i < numBuckets; i++)
            bucketArray.add(null);
    }
    public int size() { return size; }
    public boolean isEmpty() { return size() == 0; }
    //Bu metot, key in hangi index te olduğunu bulmak için hash uygular.
    private int getBucketIndex(K key) {
        int hashCode = key.hashCode();
        int index = hashCode % numBuckets;//key leri yeri icin yapilir.
        index = index < 0 ? index * -1 : index;// key.hashCode () negatif olabilir.
        return index;
    }
    //Bu metot,belirli bir key i silme işlemini gerçeklestirir.
    public V remove(K key)
    {
        int bucketIndex = getBucketIndex(key);//silinmek istenen key in hangi index te oldugunu bulmak icin hash kullanılır
        HashNode<K, V> head = bucketArray.get(bucketIndex);//listenin başına geçtik
        HashNode<K, V> prev = null;
        //listedeki key i arayalim
        while (head != null) {
            //eger key bulunursa
            if (head.key.equals(key))
                break;
            //eger bulunmazsa bulana kadar listede ilerlemeye devam.
            prev = head;
            head = head.next;
        }
        // eger key yoksa
        if (head == null)
            return null;
        size--; //eleman eksilt
        // key i silelim
        if (prev != null)
            prev.next = head.next;
        else
            bucketArray.set(bucketIndex, head.next);

        return head.value;
    }
    //aranan anahtar için People2 nesnesini(yani value) dondurur.
    public V search(K key) {
        int bucketIndex = getBucketIndex(key);//aranan key in hangi index te oldugunu bulmak icin hash kullanılır
        HashNode<K, V> head = bucketArray.get(bucketIndex);//listenin başına geçtik
        //listedeki key i arayalim
        while (head != null) {
            if (head.key.equals(key))
                return head.value;
            head = head.next;
        }
        // eger key bulunmazsa yok ise null doner
        return null;
    }
    //hash e eleman eklenir.
    public void add(K key, V value) {
        // Verilen key için basa gectik
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);

        // Key in zaten mevcut olup olmadığı kontrol edilir.
        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;//aynı key e sahip olan elemanların pes pese dizilmesi saglanir.
                return;
            }
            head = head.next;//hash in tamamı dolasilir. Aynı key degeri varsa pes pese eklendikten sonra donguden cikilir.
        }
        //hash e eleman eklenir.
        size++;//boyut artırılır
        head = bucketArray.get(bucketIndex);
        HashNode<K, V> newNode
                = new HashNode<K, V>(key, value); //node eklenir
        newNode.next = head;
        bucketArray.set(bucketIndex, newNode);

        // Yük faktörü eşiğin ötesine geçerse, çift hash tablo boyutu
        if ((1.0 * size) / numBuckets >= 0.7) {
            ArrayList<HashNode<K, V> > temp = bucketArray;
            bucketArray = new ArrayList<>();
            numBuckets = 2 * numBuckets;
            size = 0;
            for (int i = 0; i < numBuckets; i++)
                bucketArray.add(null);

            for (HashNode<K, V> headNode : temp) {
                while (headNode != null) {
                    add(headNode.key, headNode.value);
                    headNode = headNode.next;
                }
            }
        }
    }
    /*Bu metot, aynı harfle baslayan harflerin key lerinin aynı olmasını sağlar bu sayede
     aynı keye sahip olanlar pes pese dizilir.*/
    public String keyAtamasi(String s){
        if(Character.compare(s.charAt(0),'N')==0){
            return "N";
        }else if(Character.compare(s.charAt(0),'C')==0){
            return "C";
        }else if(Character.compare(s.charAt(0),'E')==0){
            return "E";
        }else if(Character.compare(s.charAt(0),'M')==0){
            return "M";
        }
        return null;
    }
    // Map sınıfını test etmek için main çalıştırılır.
    public static void main(String[] args)
    {
        Map<String, People2> map = new Map<>();
        String numbers[] = new String[20];
        String names[] = {"Nur","Nisa","Nil","Naz","Nalan","Cenk","Cemil","Coskun","Cemal","Can","Elif","Esra","Ezgi","Emel","Ece"
        ,"Mehmet","Melih","Melisa","Melek","Meriç"};
        //telefon listesinde olan 20 kişinin numaraları rasgele atılır.
        for(int i=0;i<20;i++){
            if(i>=10){
                numbers[i] = "538 914 39 6"+(i-10);
            }else{
                numbers[i] = "535 645 10 7"+i;
            }
            //System.out.println(numbers[i]);
        }
        //20 kişinin isim ve telefon numarasının, hash e eklenmesi islemi gerceklestirilir.
        for(int j=0;j<20;j++){
            String mykey = map.keyAtamasi(names[j]);
            map.add(mykey, new People2(numbers[j],names[j]));
        }
        //fonksiyonlar kullanılarak telefon listesinde islemler gerceklestirilir.
        System.out.println("Hash Yapısının Boyutu: "+map.size()); //N,C,E,M den oluşan hash yapımızın boyutu 4 dür.
        System.out.println("Silinen N keyi: "+map.remove("N")); //hash yapısındaki key i N olan kısım silinir.
        System.out.println("Silinen C keyi: "+map.remove("C")); //hash yapısındaki key i C olan kısım silinir.
        System.out.println("2 key silindikten sonra boyut: "+map.size()); //hash yapısından 2 key silindiğinden dolayı 4-2=2 boyuta sahiptir.
        System.out.println("M keyi: "+map.search("M"));
        System.out.println("Hash bos mu? "+map.isEmpty());
    }
}
