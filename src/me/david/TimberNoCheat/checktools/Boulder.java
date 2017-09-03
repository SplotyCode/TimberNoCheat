package me.david.TimberNoCheat.checktools;

import me.david.TimberNoCheat.checktools.location.Box3D;
import me.david.TimberNoCheat.checktools.location.Location;

public interface Boulder extends Location {

    Boulder setRay(double startX, double startY, double startZ,
                                    double dirX, double dirY, double dirZ);

    Boulder setAABB(double targetX, double targetY, double targetZ,
                                     double boxMarginHorizontal, double boxMarginVertical);
    Boulder setAABB(Box3D box);

    Boulder setAABB(int targetX, int targetY, int targetZ, double margin);

    Boulder setAABB(double minX, double minY, double minZ,
                                    double maxX, double maxY, double maxZ);
    Boulder setFindNearestPointIfNotCollide(boolean findNearestPointIfNotCollide);
    boolean getFindNearestPointIfNotCollide();
    Boulder loop();
    boolean collides();
    double getClosestDistanceSquared();
    double getTime();
}
