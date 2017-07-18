
package tnet.communication;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TNetData<D> implements Serializable
{

    private D data;

    private int clientId;

    public TNetData(D data, int clientId)
    {
        this.data = data;
        this.clientId = clientId;
    }

    public int getClientId()
    {
        return clientId;
    }

    public D getData()
    {
        return data;
    }

}
