package FinalBuild;

public class Player {
    private double x, y, rad, xDelta, yDelta, rayY, rayX, rayYoff, rayXoff, rayHLength, rayVLength, vRayX, hRayX, hRayY, vRayY, yRay, xRay, distance;
    private final float rotationVal;
    private final double pi;
    private final int fov;
    private final double degToRad = Math.toRadians(0.3);
    private final int size, multiplier;

    private final int WIDTH = Display.WIDTH / 2;
    private final int HEIGHT = Display.HEIGHT;

    public Player() {
        this.y = HEIGHT / 2.0;
        System.out.println(y);
        this.x = WIDTH / 2.0;
        System.out.println(x);
        this.size = 20;
        pi = Math.PI;
        rad = (float) (pi / 2);
        multiplier = 5;
        rotationVal = 0.1f;
        fov = 120;
    }

    public void calculateRay() {
        double radFOV = rad - degToRad * fov;
        if (radFOV < 0) radFOV += 2 * pi;
        if (radFOV > Math.PI * 2) radFOV -= 2 * pi;
        //for (int i = 0; i < fov * 2; i++) {
            for (int i = 0; i < fov * 2; i++) {
                
            
            checkHorizontal(radFOV);
            checkVertical(radFOV);

            MapRender.renderRays();

            if (rayHLength < rayVLength) {
                xRay = hRayX;
                yRay = hRayY;
                distance = rayHLength;
            } else {
                xRay = vRayX;
                yRay = vRayY;
                distance = rayVLength;
            }

            Display.dimensionalRender.draw3DWalls(i, fov);

            radFOV += degToRad;
            if (radFOV < 0) radFOV += 2 * pi;
            if (radFOV > Math.PI * 2) radFOV -= 2 * pi;

        }
    }

    public void moveUp() {
        System.out.println(y);
        System.out.println(x);
        y -= yDelta;
        x += xDelta;
    }

    public void moveDown() {
        y += yDelta;
        x -= xDelta;
    }

    public void rotateLeft() {
        rad += rotationVal;
        if (rad > 2 * pi) rad = 0;

        xDelta = Math.cos(rad) * multiplier;
        yDelta = Math.sin(rad) * multiplier;
    }

    public void rotateRight() {
        rad -= rotationVal;
        if (rad < 0) rad = (2 * pi);

        xDelta = Math.cos(rad) * multiplier;
        yDelta = Math.sin(rad) * multiplier;
    }

    public void checkHorizontal(double radFOV) {
        int dof = 0;
        int dofEnd = Display.map.getXLength();
        double yRayDelta = y % Display.yTILE;
        boolean up = true;
        if (radFOV < pi) {

            rayX = -yRayDelta / Math.tan(radFOV);
            rayX = x - rayX;
            rayY = y - yRayDelta;

            rayYoff = Display.yTILE;
            rayXoff = rayYoff / -Math.tan(radFOV);

        }
        if (radFOV > pi) {
            double tmp = -yRayDelta + Display.yTILE;

            rayX = tmp / Math.tan(radFOV);
            rayX = x - rayX;
            rayY = y + tmp;


            rayYoff = -Display.yTILE;
            rayXoff = rayYoff / -Math.tan(radFOV);
            up = false;
        }
        if (radFOV == pi || radFOV == pi * 2 || radFOV == 0) {

            rayX = x;
            rayY = y;
            dof = dofEnd;
            up = false;

        }

        while (dof < dofEnd) {
            int indexX = (int) (rayX) / Display.xTILE;
            //   int indexY = (int) (rayY -1) / Display.tileY;
            int indexY = up ? (int) (rayY - 1) : (int) (rayY + 1);
            indexY = indexY / (Display.yTILE);

            if (0 <= indexX && indexX < Display.map.getXLength() && 0 <= indexY && indexY < Display.map.getYLength() && Display.map.getValue(indexY, indexX) == 1) {
                dof = dofEnd;
            } else {
                rayX -= rayXoff;
                rayY -= rayYoff;
                dof++;
            }
        }
        hRayX = rayX;
        hRayY = rayY;

        rayHLength = Math.sqrt(Math.pow(x - rayX, 2) + Math.pow(y - rayY, 2));
    }

    public void checkVertical(double radFOV) {
        int dof = 0;
        int dofEnd = Display.map.getXLength();
        double xRayDelta = x % Display.xTILE;

        boolean left = true;
        if (radFOV < (3 * pi / 2) && radFOV > pi / 2) {

            // xRayDelta = Display.tileX - xRayDelta;

            rayX = xRayDelta;
            rayX = x - rayX;
            rayY = xRayDelta * Math.tan(radFOV);
            rayY = y + rayY;

            rayXoff = Display.xTILE;
            rayYoff = rayXoff * -Math.tan(radFOV);

        }
        if (radFOV > 3 * pi / 2 || radFOV < pi / 2) {
            xRayDelta = Display.xTILE - xRayDelta;
            rayX = xRayDelta;
            rayX = x + rayX;
            rayY = xRayDelta * Math.tan(radFOV);
            rayY = y - rayY;

            rayXoff = -Display.xTILE;
            rayYoff = -rayXoff * Math.tan(radFOV);

            left = false;
        }
        if (radFOV == pi / 2 || radFOV == (3 * pi / 2)) {

            rayX = x;
            rayY = y;

            dof = dofEnd;
            left = false;
        }

        while (dof < dofEnd) {

            int indexY = (int) (rayY) / Display.xTILE;
            //   int indexY = (int) (rayY -1) / Display.tileY;
            int indexX = left ? (int) (rayX - 1) : (int) (rayX + 1);
            indexX /= Display.xTILE;

            if (0 <= indexX && indexX < Display.map.getXLength() && 0 <= indexY && indexY < Display.map.getYLength() && Display.map.getValue(indexY, indexX) == 1) {
                dof = dofEnd;
            } else {
                rayX -= rayXoff;
                rayY -= rayYoff;
                dof++;
            }
        }

        vRayX = rayX;
        vRayY = rayY;

        rayVLength = Math.sqrt(Math.pow(x - rayX, 2) + Math.pow(y - rayY, 2));
    }


    public double getDistance() {
        return distance;
    }

    public double getxRay() {
        return xRay;
    }

    public double getyRay() {
        return yRay;
    }

    public int[] playerModelCoords() {
        return new int[]{
                (int) x - size / 2, (int) x + size / 2,
                (int) y - size / 2, (int) y + size / 2
        };
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getxDelta() {
        return xDelta;
    }

    public double getyDelta() {
        return yDelta;
    }
}
