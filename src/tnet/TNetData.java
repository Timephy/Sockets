
package tnet;

public class TNetData<D>
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
