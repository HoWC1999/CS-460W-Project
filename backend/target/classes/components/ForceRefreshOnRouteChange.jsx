// src/components/ForceRefreshOnTopLevelRouteChange.js
import { useEffect, useRef } from "react";
import { useLocation } from "react-router-dom";

const ForceRefreshOnTopLevelRouteChange = () => {
    const location = useLocation();
    const prevTopLevel = useRef("");

    useEffect(() => {
        // Extract the top-level route (first segment after the slash)
        const currentTopLevel = location.pathname.split("/")[1] || "";
        if (prevTopLevel.current && currentTopLevel !== prevTopLevel.current) {
            // Force a reload only when the top-level route changes
            window.location.reload();
        }
        prevTopLevel.current = currentTopLevel;
    }, [location.pathname]);

    return null;
};

export default ForceRefreshOnTopLevelRouteChange;
