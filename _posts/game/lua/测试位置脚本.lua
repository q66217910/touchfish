function OnEvent(event, arg)
      if(event == 'MOUSE_BUTTON_RELEASED' and arg==4 ) then
            x, y = GetMousePosition();
            OutputLogMessage("Mouse is at %d, %d\n", x, y);
     end
end