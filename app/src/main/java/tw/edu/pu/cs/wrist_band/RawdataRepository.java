package tw.edu.pu.cs.wrist_band;

import android.app.Application;

import java.util.List;

public class RawdataRepository {

    private RawdataDao rawdao;

    RawdataRepository(Application application) {
        BandRoomDatabase db = BandRoomDatabase.getDatabase(application);
        System.out.println(db);
        rawdao = db.rawdataDao();
        Rawdata data = Initializer();
        addRawdata(rawdao,data);
        getRawdata(rawdao);
    }

    public Rawdata Initializer(){
        Rawdata rdata=new Rawdata("001","abcd1ed3","60","1000","123","100");
        return rdata;
    }

    public void addRawdata(RawdataDao rawdao,Rawdata data){
        rawdao.insert(data);
    }

    public void getRawdata(RawdataDao rawdao){
        List<Rawdata> rawdataList = rawdao.getall();
    }

}
