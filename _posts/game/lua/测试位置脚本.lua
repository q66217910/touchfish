function OnEvent(event, arg)
      if(event == 'MOUSE_BUTTON_RELEASED' and arg==5 ) then
            x, y = GetMousePosition();
            OutputLogMessage("Mouse is at %d, %d\n", x, y);
     end
     if(event == 'MOUSE_BUTTON_RELEASED' and arg==5 ) then
        move()
     end
end

--[[
ÒÆ¶¯
]]
function move(x,y)
	MoveMouseTo(x,y)
    Sleep(500)
    PressMouseButton(1)
    Sleep(100)
    ReleaseMouseButton(1)
    Sleep(1000)
end