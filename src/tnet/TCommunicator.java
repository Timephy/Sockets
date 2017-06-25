
package tnet;

import java.io.IOException;

@Deprecated
public interface TCommunicator
{
    public <D> D read() throws IOException;

    public <D> void write(D obj) throws IOException;

    //public String getLocalIp();
}
