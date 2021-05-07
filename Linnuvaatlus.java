// Mario Käära. Objektorienteeritud programmeerimine KT2

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Linnuvaatlus {


    private static List<Lind> loeLinnud(String failinimi) throws IOException {
        List<Lind> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(failinimi), StandardCharsets.UTF_8))) {
            String rida = br.readLine();
            int reaNr = 1;
            while (rida != null) {
                String[] osad = rida.split(",");
                char tähemärk = osad[2].charAt(0);
                try {
                    Lind lind = new Lind(osad[0], osad[1], tähemärk, osad[3]);
                    list.add(lind);
                } catch (ValeSuguErind e) {
                    System.out.println(e.getMessage() + " failis real nr: " + reaNr);
                }
                rida = br.readLine();
                reaNr += 1;
            }
            return list;
        }
    }

    private static Map<String, Integer> liigiKaupa(List<Lind> lindudeList) {
        Map<String, Integer> hashmap = new HashMap<>();
        for (Lind lind : lindudeList) {
            Integer count = hashmap.get(lind.getLiik());
            if (count == null) {
                hashmap.put(lind.getLiik(), 1);
            } else {
                hashmap.put(lind.getLiik(), count + 1);
            }
        }
        return hashmap;
    }

    public static void main(String[] args) throws IOException {

        List<Lind> linnud = loeLinnud("linnud.txt");
        Scanner sc = new Scanner(System.in);
        System.out.println("Kas soovid lisada (L), vaadata (V) või salvestada ja lõpetada (S)? ");
        String valik = sc.nextLine();

        while (!valik.equalsIgnoreCase("S")) {
            if (valik.equalsIgnoreCase("L")) {
                System.out.println("Sisesta linnu andmed kujul: liik,värv,sugu,kaitse_all_olek:");
                String lind = sc.nextLine();
                String[] osad = lind.split(",");
                char tähemärk = osad[2].charAt(0);
                try {
                    Lind linnuIsend = new Lind(osad[0], osad[1], tähemärk, osad[3]);
                    linnud.add(linnuIsend);
                } catch (ValeSuguErind e) {
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
            } else if (valik.equalsIgnoreCase("V")) {
                Map<String, Integer> hashmap = liigiKaupa(linnud);
                System.out.println("Seni oled näinud linde " + hashmap.keySet());
                System.out.println("Sisesta linnu nimi kelle esinemissagedust soovid teada: ");
                String linnuEsinemine = sc.nextLine();
                if (!hashmap.containsKey(linnuEsinemine)) {
                    System.exit(0);
                }
                System.out.println("Oled näinud liiki " + linnuEsinemine + " " + hashmap.get(linnuEsinemine) + " kord(a).");
            }
            System.out.println("Kas soovid lisada (L), vaadata (V) või salvestada ja lõpetada (S)? ");
            valik = sc.nextLine();
        }

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("linnud.dat"))) {
            dos.writeInt(linnud.size());
            for (Lind lind : linnud) {
                dos.writeUTF(lind.getLiik());
                dos.writeUTF(lind.getVärv());
                dos.writeChar(lind.getSugu());
                dos.writeBoolean(lind.isKaitseAll());
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("kaitsealused.txt")))) {
            for (Lind lind : linnud) {
                if (lind.isKaitseAll()) {
                    bw.write(lind.toString());
                }
            }
        }
        System.out.println("Linnud on salvestatud!");
    }
}