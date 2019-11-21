package tw.edu.pu.cs.wrist_band;

import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

class BinaryRequest extends Request<byte[]> {

    /** Lock to guard mListener as it is cleared on cancel() and read on delivery. */
    private final Object mLock = new Object();

    private Response.Listener<byte[]> mListener;

    BinaryRequest(int method, String url, Response.Listener<byte[]> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode == 200) {
            return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
        } else {
            return null;
        }
    }

    @Override
    protected void deliverResponse(byte[] response) {
        Response.Listener<byte[]> listener;
        synchronized (mLock) {
            listener = mListener;
        }
        if (listener != null) {
            listener.onResponse(response);
        }
    }

    @Override
    public void cancel() {
        super.cancel();
        synchronized (mLock) {
            mListener = null;
        }
    }
}
