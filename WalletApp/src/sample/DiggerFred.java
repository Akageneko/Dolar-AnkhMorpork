package sample;

public class DiggerFred{

    Thread fred;
    Secretary secretary;

    public DiggerFred(Secretary secretary){
        this.secretary = secretary;
    }

    public void startDigging(Block block, int numberOfZeros) throws InterruptedException {
        fred = new Thread(){
            @Override
            public void run() {
                long nonce = GeneratorConector.Mine(block.toString(),numberOfZeros);
                block.SetNonce(""+nonce);
                sendMinedBlock(block);
            }
        };
    }

    public void stopDigging(){
        if(fred!=null){
            fred.stop();
        }
    }

    public void sendMinedBlock(Block block){
        secretary.sendBlock(block);
    }

}
