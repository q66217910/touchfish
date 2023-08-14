function OnEvent(event, arg)
     if(event == 'MOUSE_BUTTON_PRESSED' and arg==2 ) then
            x, y = GetMousePosition();
            OutputLogMessage("Mouse is at %d, %d\n", x, y);
     end
end