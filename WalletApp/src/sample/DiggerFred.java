package sample;

public class DiggerFred{

    Thread fred;
    Secretary secretary;

    public DiggerFred(Secretary secretary){
        this.secretary = secretary;
    }

    public void startDigging(Block block, int numberOfZeros) throws InterruptedException {
        System.out.println("DEBUG: in DiggerFred startDiggind");
        fred = new Thread(){
            @Override
            public void run() {
                System.out.println("DEBUG: in worker digger");
                long nonce = GeneratoValidator.Mine(block.toString(),numberOfZeros);
                block.SetNonce(""+nonce);
                System.out.println("DEBUG: Mined, sending");
                sendMinedBlock(block);
                System.out.println("DEBUG: sent, quitting");
            }
        };
        fred.start();
    }

    public void stopDigging(){
        if(fred!=null){
            fred.stop();
        }
    }

    public void sendMinedBlock(Block block){
        secretary.sendBlock(block);
        secretary.economy.getBlockchain().AddBlockToChain(block);
        secretary.economy.balance = secretary.economy.getBlockchain().GetUserAccountBalance(secretary.economy.settings.getPublicKeyString());
    }

}
