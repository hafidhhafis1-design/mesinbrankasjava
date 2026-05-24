/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package belajaroop;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.OutputStream;
/**
 *
 * @author USER
 */
public class backendaplikasi {
    
    static int saldoUtama = 0;
    static int sisaKesempatan = 3; // Tambahin ini di luar!

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        server.createContext("/topup", (exchange -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            String query = exchange.getRequestURI().getQuery();
            String jawabanDariJava = "";
            int statusCode = 200; 
            
            // 2. CEK DULU: APAKAH AKUN UDAH DIBLOKIR?
            if (sisaKesempatan <= 0) {
                jawabanDariJava = "AKSES DIBLOKIR PERMANEN! LU UDAH SALAH 3 KALI!";
                statusCode = 403;
                System.out.println("[BLOKIR] Ada yang maksa masuk ke akun yang ke-blokir.");
            } 
            // 3. KALAU BELUM DIBLOKIR, LANJUT CEK PIN
            else {
                if (query != null && query.contains("pin=123456")) {
                    saldoUtama = saldoUtama + 50000;
                    sisaKesempatan = 3; // Kalau berhasil login, kesempatan di-reset penuh lagi
                    jawabanDariJava = String.valueOf(saldoUtama);
                    System.out.println("[AKSES DITERIMA] Saldo: Rp " + saldoUtama);
                } else {
                    sisaKesempatan--; // Kesempatan dikurangin 1
                    jawabanDariJava = "PIN SALAH! Sisa kesempatan lu: " + sisaKesempatan;
                    statusCode = 403; 
                    System.out.println("[PERINGATAN] PIN salah. Sisa: " + sisaKesempatan);
                }
            }
            
            // 4. Ngirim struk balasan ke HP
            exchange.sendResponseHeaders(statusCode, jawabanDariJava.length());
            OutputStream os = exchange.getResponseBody();
            os.write(jawabanDariJava.getBytes());
            os.close();
        }));
        
        server.start();
        System.out.println("🔥 BRANKAS JAVA AKTIF! Menunggu verifikasi PIN...");
    }
                
}