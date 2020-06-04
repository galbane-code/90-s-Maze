package algorithms.mazeGenerators;

/**
 *A class that helps us in kroskal algorithm
 *Kruskal works with groups of edges in the unite/find functions
 */
class Edge
{

   private Position x;
   private Position y;

   public Edge(Position x, Position y)
   {
       this.x = x;
       this.y = y;
   }

    /**
     * Getters and Setters
     */
   //x position
   public Position getX() {
       return x;
   }
   public void setX(Position x) {
       this.x = x;
   }

   //y position
   public Position getY() {
       return y;
   }
   public void setY(Position y) {
       this.y = y;
   }
}
