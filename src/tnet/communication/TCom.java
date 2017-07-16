
package tnet.communication;

public interface TCom
{

    public <D> void write(D obj);

    public <D> TNetData<D> read();

    public boolean canCommunicate();

}
