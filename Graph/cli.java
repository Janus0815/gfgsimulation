package Graph;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class cli {
    private static final Logger log = Logger.getLogger(cli.class.getName());
    private String[] args = null;
    private Options options = new Options();

    private static int xMax = 500;					//width of the plane
    private static int yMax = 500;					//length of the plane
    private static int numNodes = 50;				//number of nodes
    private static long seedx = 21;				//seed for x-coordinate
    private static long seedy = 42;				//seed for y-coordinate
    private static int sourceNode = 3;				//Routing: source
    private static int destinationNode = 1;		//Routing: destination


    public void setArgs(String[] arg) {
        this.args = arg;
    }

    public void init() {


        options.addOption("h", "help", false, "show help.");

        options.addOption("s", "sourceNode", true , "Source Node");
        options.addOption("d", "destinationNode", true, "Destination Node");
        options.addOption("sx", "seedX", true, "Seed for X");
        options.addOption("sy", "seedY", true, "Seed for Y");
        options.addOption("n", "numNodes", true, "Number of Nodes");
        options.addOption("yM", "yMax", true, "Y size of planar space");
        options.addOption("xM", "xMax", true, "X size of planar space");

    }

    public void parse() {
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);

            //help
            if (cmd.hasOption("h"))
                help();

            //Source Node
            if (cmd.hasOption("s")) {
                setsourceNode(Integer.parseInt(cmd.getOptionValue("s")));
            } else {
                log.log(Level.SEVERE, "Source Node not provided, Fallback to default: " + this.getsourceNode());
                //help(); //activate if app shall abort if value not provided
            }
            //Destination Node
            if (cmd.hasOption("d")) {
                setdestinationNode(Integer.parseInt(cmd.getOptionValue("d")));
            } else {
                log.log(Level.SEVERE, "Destination Node not provided , Fallback to default: " +  this.getdestinationNode());
            }

            //Seed X
            if (cmd.hasOption("sx")) {
                setseedx(Long.parseLong(cmd.getOptionValue("sx")));
            } else {
                log.log(Level.SEVERE, "Seed X not provided , Fallback to default:" + this.getseedx() );
            }

            //Seed Y
            if (cmd.hasOption("sy")) {
                setseedy(Long.parseLong(cmd.getOptionValue("sy")));
            } else {
                log.log(Level.SEVERE, "Seed Y not provided , Fallback to default: " + this.getseedy());
            }

            //Number of Nodes
            if (cmd.hasOption("n")) {
                setnumNodes(Integer.parseInt(cmd.getOptionValue("n")));
            } else {
                log.log(Level.SEVERE, "Number of Nodes not provided , Fallback to default: " + this.getnumNodes());
            }

            //Y Max
            if (cmd.hasOption("yM")) {
                setyMax(Integer.parseInt(cmd.getOptionValue("yM")));
            } else {
                log.log(Level.SEVERE, "y Max not provided , Fallback to default: " + this.getyMax());
            }

            //X Max
            if (cmd.hasOption("xM")) {
                setyMax(Integer.parseInt(cmd.getOptionValue("xM")));
            } else {
                log.log(Level.SEVERE, "x Max not provided , Fallback to default: " + this.getyMax());
            }



        } catch (ParseException e) {
            log.log(Level.SEVERE, "Failed to parse comand line properties", e);
            help();
        }
    }

    private void help() {
        // This prints out some help
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "Dingsie", options );
        System.exit(0);
    }

    public void setxMax (int x) {
        this.xMax = x;
    }

    public int getxMax () {
        return this.xMax;
    }

    public void setyMax (int y) {
        this.yMax = y;
    }

    public int getyMax () {
        return this.yMax;
    }

    //number of Nodes
    public void setnumNodes (int n) {
        this.numNodes = n;
    }

    public int getnumNodes() {
        return this.numNodes;
    }

    //seed X
    public void setseedx (long sx) {
        this.seedx = sx;
    }

    public long getseedx() {
        return this.seedx;
    }

    //seed Y
    public void setseedy (long sy) {
        this.seedy = sy;
    }

    public long getseedy() {
        return this.seedy;
    }

    //source Node
    public void setsourceNode (int sn) {
        this.sourceNode = sn;
    }

    public int getsourceNode() {
        return this.sourceNode;
    }

    //destination Node
    public void setdestinationNode (int dn) {
        this.destinationNode = dn;
    }

    public int getdestinationNode() {
        return this.destinationNode;
    }

}