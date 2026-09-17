package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import fungsi.sekuel;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author windiartonugroho
 */
public class ApiOrthanc {
    private HttpHeaders headers ;
    private JsonNode root;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private sekuel Sequel=new sekuel();
    private SSLContext sslContext;
    private SSLSocketFactory sslFactory;
    private Scheme scheme;
    private HttpComponentsClientHttpRequestFactory factory;
    private String auth,authEncrypt,requestJson;
    private SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");
    private byte[] encodedBytes;
    private int i=1;
    
    public ApiOrthanc(){
        try {
            auth=koneksiDB.USERORTHANC()+":"+koneksiDB.PASSORTHANC();
            encodedBytes = Base64.encodeBase64(auth.getBytes());
            authEncrypt= new String(encodedBytes);
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }
    
    public String Auth(){
        return authEncrypt;
    }
    
    public JsonNode AmbilSeries(String Norm,String Tanggal1,String Tanggal2){
        System.out.println("Percobaan Mengambil Photo Pasien : "+Norm);
        try{
            headers = new HttpHeaders();
            System.out.println("Auth : "+authEncrypt);
            headers.add("Authorization", "Basic "+authEncrypt);
            requestJson = "{"+
                              "\"Level\": \"Study\","+
                              "\"Expand\": true,"+
                              "\"Query\": {"+
                                   "\"StudyDate\": \""+Tanggal1+"-"+Tanggal2+"\","+
                                   "\"PatientID\": \""+Norm+"\""+
                              "}"+
                          "}";
            System.out.println("Request JSON : "+requestJson);
            requestEntity = new HttpEntity(requestJson,headers);
            System.out.println("URL : "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/tools/find");
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/tools/find", HttpMethod.POST, requestEntity, String.class).getBody();
            System.out.println("Result JSON : "+requestJson);
            root = mapper.readTree(requestJson);
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
            JOptionPane.showMessageDialog(null,"Gagal mengambil data dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    
    public JsonNode AmbilPng(String NoRawat,String Series,String norawatslash){
        System.out.println("Percobaan Mengambil Gambar PNG : "+NoRawat+", Series : "+Series);
        try{
            headers = new HttpHeaders();
            System.out.println("Auth : "+authEncrypt);
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series, HttpMethod.GET, requestEntity, String.class).getBody();
            System.out.println("Result JSON : "+requestJson);
            root = mapper.readTree(requestJson);
            i=1;
            for(JsonNode list:root.path("Instances")){
                 System.out.println("Mengambil Gambar PNG "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview");
                 headers = new HttpHeaders();
                 headers.add("Authorization", "Basic "+authEncrypt);
                 headers.add("Accept","image/png");
                 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                 headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                 HttpEntity<String> entity = new HttpEntity<>(headers);
                 ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview", HttpMethod.GET, entity, byte[].class);
                 Files.write(Paths.get("./gambarradiologi/"+NoRawat+i+".png"),response.getBody());   
       //        Menambahkan fitur simpan gambar radiologi dari orthanc
                 uploadImage(NoRawat+i+".png","pages/upload");     
                 Sequel.menyimpantf("gambar_radiologi","?,?,?,?","No.Rawat",4,new String[]{
                                norawatslash,tanggalNow.format(new Date()),jamNow.format(new Date()),"pages/upload/"+NoRawat+i+".png"
                            });
                i++; 
            } 
            JOptionPane.showMessageDialog(null,"Penyimpanan Gambar PNG dari Orthanc ke Webapps berhasil");
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
            JOptionPane.showMessageDialog(null,"Gagal mengambil Gambar PNG dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    
    public JsonNode AmbilPngUsg(String NoRawat,String Series,String norawatslash){
        System.out.println("Percobaan Mengambil Gambar PNG : "+NoRawat+", Series : "+Series);
        try{
            headers = new HttpHeaders();
            System.out.println("Auth : "+authEncrypt);
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series, HttpMethod.GET, requestEntity, String.class).getBody();
            System.out.println("Result JSON : "+requestJson);
            root = mapper.readTree(requestJson);
            i=1;
            for(JsonNode list:root.path("Instances")){
                 System.out.println("Mengambil Gambar PNG "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview");
                 headers = new HttpHeaders();
                 headers.add("Authorization", "Basic "+authEncrypt);
                 headers.add("Accept","image/png");
                 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                 headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                 HttpEntity<String> entity = new HttpEntity<>(headers);
                 ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview", HttpMethod.GET, entity, byte[].class);
                 Files.write(Paths.get("./gambarradiologi/"+NoRawat+i+".png"),response.getBody());   
       //        Menambahkan fitur simpan gambar radiologi dari orthanc
                 uploadImageUsg(NoRawat+i+".png","pages/upload");     
                 Sequel.menyimpantf("hasil_pemeriksaan_usg_gambar","?,?","No.Rawat",2,new String[]{
                                norawatslash,"pages/upload/"+NoRawat+i+".png"
                            });
                i++; 
            } 
            JOptionPane.showMessageDialog(null,"Penyimpanan Gambar PNG dari Orthanc ke Webapps berhasil");
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
            JOptionPane.showMessageDialog(null,"Gagal mengambil Gambar PNG dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    
    public JsonNode AmbilJpg(String NoRawat,String Series,String norawatslash){
        System.out.println("Percobaan Mengambil Gambar JPG : "+NoRawat+", Series : "+Series);
        try{
            headers = new HttpHeaders();
            System.out.println("Auth : "+authEncrypt);
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series, HttpMethod.GET, requestEntity, String.class).getBody();
            System.out.println("Result JSON : "+requestJson);
            root = mapper.readTree(requestJson);
            i=1;
            for(JsonNode list:root.path("Instances")){
                 System.out.println("Mengambil Gambar JPG "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview");
                 headers = new HttpHeaders();
                 headers.add("Authorization", "Basic "+authEncrypt);
                 headers.add("Accept","image/jpeg");
                 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                 headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                 HttpEntity<String> entity = new HttpEntity<>(headers);
                 ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview", HttpMethod.GET, entity, byte[].class);
                 Files.write(Paths.get("./gambarradiologi/"+NoRawat+i+".jpg"),response.getBody());
                 //  Menambahkan fitur simpan gambar radiologi dari orthanc
                 uploadImage(NoRawat+i+".jpg","pages/upload");     
                 Sequel.menyimpantf("gambar_radiologi","?,?,?,?","No.Rawat",4,new String[]{
                                norawatslash,tanggalNow.format(new Date()),jamNow.format(new Date()),"pages/upload/"+NoRawat+i+".jpg"
                            });
                i++; 
            }
            JOptionPane.showMessageDialog(null,"Penyimpanan Gambar JPG dari Orthanc ke Webapps berhasil");
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
            JOptionPane.showMessageDialog(null,"Gagal mengambil Gambar JPG dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    public JsonNode AmbilJpg2(String Series){
    return null;
}
    
    public JsonNode AmbilJpgUsg(String NoRawat,String Series,String norawatslash){
        System.out.println("Percobaan Mengambil Gambar JPG : "+NoRawat+", Series : "+Series);
        try{
            headers = new HttpHeaders();
            System.out.println("Auth : "+authEncrypt);
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series, HttpMethod.GET, requestEntity, String.class).getBody();
            System.out.println("Result JSON : "+requestJson);
            root = mapper.readTree(requestJson);
            i=1;
            for(JsonNode list:root.path("Instances")){
                 System.out.println("Mengambil Gambar JPG "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview");
                 headers = new HttpHeaders();
                 headers.add("Authorization", "Basic "+authEncrypt);
                 headers.add("Accept","image/jpeg");
                 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                 headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                 HttpEntity<String> entity = new HttpEntity<>(headers);
                 ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview", HttpMethod.GET, entity, byte[].class);
                 Files.write(Paths.get("./gambarradiologi/"+NoRawat+i+".jpg"),response.getBody());
                 //  Menambahkan fitur simpan gambar radiologi dari orthanc
                 uploadImageUsg(NoRawat+i+".jpg","pages/upload");     
                 Sequel.menyimpantf("hasil_pemeriksaan_usg_gambar","?,?","No.Rawat",2,new String[]{
                                norawatslash,"pages/upload/"+NoRawat+i+".jpg"
                            });
                i++; 
            }
            JOptionPane.showMessageDialog(null,"Penyimpanan Gambar JPG dari Orthanc ke Webapps berhasil");
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
            JOptionPane.showMessageDialog(null,"Gagal mengambil Gambar JPG dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    
    public JsonNode AmbilBmp(String NoRawat,String Series){
        System.out.println("Percobaan Mengambil Gambar BMP : "+NoRawat+", Series : "+Series);
        try{
            headers = new HttpHeaders();
            System.out.println("Auth : "+authEncrypt);
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series, HttpMethod.GET, requestEntity, String.class).getBody();
            System.out.println("Result JSON : "+requestJson);
            root = mapper.readTree(requestJson);
            i=1;
            for(JsonNode list:root.path("Instances")){
                 System.out.println("Mengambil Gambar BMP "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview");
                 headers = new HttpHeaders();
                 headers.add("Authorization", "Basic "+authEncrypt);
                 headers.add("Accept","image/bmp");
                 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                 headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                 HttpEntity<String> entity = new HttpEntity<>(headers);
                 ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview", HttpMethod.GET, entity, byte[].class);
                 Files.write(Paths.get("./gambarradiologi/"+NoRawat+i+".bmp"),response.getBody());
                 i++;
            }
            JOptionPane.showMessageDialog(null,"Pengambilan Gambar BMP dari Orthanc berhasil, silahkan lihat di dalam folder Aplikasi..!!");
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
            JOptionPane.showMessageDialog(null,"Gagal mengambil Gambar BMP dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    
    public JsonNode AmbilDcm(String NoRawat,String Series){
    JOptionPane.showMessageDialog(null,
        "Fitur Download DCM belum tersedia");
    return null;
}
    
    public JsonNode AmbilJpg(String Series){
    System.out.println("Percobaan Mengambil Gambar JPG Series : "+Series);
    try{
        headers = new HttpHeaders();
        headers.add("Authorization", "Basic "+authEncrypt);
        requestEntity = new HttpEntity(headers);

        requestJson=getRest().exchange(
            koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series,
            HttpMethod.GET,
            requestEntity,
            String.class
        ).getBody();

        root = mapper.readTree(requestJson);

        for(JsonNode list : root.path("Instances")){
            headers = new HttpHeaders();
            headers.add("Authorization", "Basic "+authEncrypt);
            headers.add("Accept","image/jpeg");
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<byte[]> response = getRest().exchange(
                koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview",
                HttpMethod.GET,
                entity,
                byte[].class
            );

            Files.write(
                Paths.get("./gambarradiologi/"+Series+".jpg"),
                response.getBody()
            );

            break;
        }
    }catch(Exception e){
        System.out.println("Notifikasi : "+e);
    }

    return root;
}
    
    public RestTemplate getRest() throws NoSuchAlgorithmException, KeyManagementException {
        sslContext = SSLContext.getInstance("SSL");
        TrustManager[] trustManagers= {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {return null;}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
            }
        };
        sslContext.init(null,trustManagers , new SecureRandom());
        sslFactory=new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        scheme=new Scheme("https",443,sslFactory);
        factory=new HttpComponentsClientHttpRequestFactory();
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        return new RestTemplate(factory);
    }
    
    void uploadImage(String FileName,String docpath){
    try{
        File file =new File("gambarradiologi/"+FileName);
        byte[] data = new byte[(int) file.length()];
        data = FileUtils.readFileToByteArray(file);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/radiologi/upload.php?doc="+docpath);
        ByteArrayBody fileData = new ByteArrayBody(data, FileName);
        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        reqEntity.addPart("file", fileData); 
        postRequest.setEntity(reqEntity);
        httpClient.execute(postRequest); 
        deleteFile();        
        }catch (Exception e){
            System.out.println("Upload error"+e);
        }
    }
    
    void uploadImageUsg(String FileName,String docpath){
    try{
        File file =new File("gambarradiologi/"+FileName);
        byte[] data = new byte[(int) file.length()];
        data = FileUtils.readFileToByteArray(file);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/hasilpemeriksaanusg/upload.php?doc="+docpath);
        ByteArrayBody fileData = new ByteArrayBody(data, FileName);
        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        reqEntity.addPart("file", fileData); 
        postRequest.setEntity(reqEntity);
        httpClient.execute(postRequest); 
       //deleteFile();        
        }catch (Exception e){
            System.out.println("Upload error"+e);
        }
    }
    
    void deleteFile(){
       File file = new File("gambarradiologi");      
        String[] myFiles;    
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]); 
                myFile.delete();
            }
        }
   }
    
}
